package ktb.hackathon.ktbgratitudediary.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import ktb.hackathon.ktbgratitudediary.domain.security.CustomUserDetails;
import ktb.hackathon.ktbgratitudediary.domain.security.JwtProperties;
import ktb.hackathon.ktbgratitudediary.domain.security.TokenInfo;
import ktb.hackathon.ktbgratitudediary.exception.Error;
import ktb.hackathon.ktbgratitudediary.exception.JwtTokenException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey =  Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey()));
    }

    public TokenInfo createToken(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).userId();
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Claims claims = Jwts.claims()
                .add("userId", userId)
                .add("auth", authorities)
                .build();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName()) // loginId
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.accessExpire().toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName()) // loginId
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.refreshExpire().toMillis()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        return new TokenInfo("Bearer", accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        Object auth = claims.get("auth");
        if (auth == null) {
            throw new JwtTokenException(Error.BROKEN_TOKEN);
        }

        Object userId = claims.get("userId");
        if (userId == null) {
            throw new JwtTokenException(Error.BROKEN_TOKEN);
        }

        Collection<GrantedAuthority> authorities = Arrays.stream(((String) auth).split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        String loginId = claims.getSubject();

        UserDetails principal = new CustomUserDetails(Long.valueOf(userId.toString()), loginId, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public Boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (ExpiredJwtException e) { // 기간 만료
            throw new JwtTokenException(Error.ACCESS_TOKEN_EXPIRED);
        } catch (Exception e) {
            throw new JwtTokenException(Error.BROKEN_TOKEN);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
