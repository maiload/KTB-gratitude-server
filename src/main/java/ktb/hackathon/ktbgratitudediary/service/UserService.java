package ktb.hackathon.ktbgratitudediary.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import ktb.hackathon.ktbgratitudediary.domain.security.TokenInfo;
import ktb.hackathon.ktbgratitudediary.entity.BlackListToken;
import ktb.hackathon.ktbgratitudediary.exception.Error;
import ktb.hackathon.ktbgratitudediary.exception.JwtTokenException;
import ktb.hackathon.ktbgratitudediary.repository.BlackListTokenRepository;
import ktb.hackathon.ktbgratitudediary.repository.UserRepository;
import ktb.hackathon.ktbgratitudediary.security.JwtTokenProvider;
import ktb.hackathon.ktbgratitudediary.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlackListTokenRepository blackListTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public void saveUser(UserDto userDto) {
        userRepository.save(userDto.toEntity());
    }

    public TokenInfo logInUser(HttpServletResponse response, UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.loginId(), userDto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenInfo tokenInfo = jwtTokenProvider.createToken(authentication);
        CookieUtil.addSecureCookie(response, tokenInfo.refreshToken());
        return tokenInfo;
    }

    public TokenInfo reissueToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = checkRefreshToken(request);
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        TokenInfo tokenInfo = jwtTokenProvider.createToken(authentication);
        CookieUtil.addSecureCookie(response, tokenInfo.refreshToken());
        return tokenInfo;
    }

    public void logOutUser(HttpServletRequest request, HttpServletResponse response){
        checkRefreshToken(request);
        CookieUtil.removeSecureCookie(response);
    }

    private String checkRefreshToken(HttpServletRequest request) {
        String refreshToken = CookieUtil.getSecureCookie(request);
        if (!StringUtils.hasText(refreshToken)) throw new JwtTokenException(Error.REFRESH_TOKEN_IS_NULL_OR_EMPTY);
        jwtTokenProvider.validateToken(refreshToken);
        Boolean isBlockedToken = blackListTokenRepository.existsByToken(refreshToken);
        if (isBlockedToken) {
            throw new JwtTokenException(Error.BLACKLIST_TOKEN);
        }
        else {
            blackListTokenRepository.save(BlackListToken.of(refreshToken));
            return refreshToken;
        }
    }

    public UserDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(UserDto::from)
                .orElseThrow(EntityNotFoundException::new);
    }
}
