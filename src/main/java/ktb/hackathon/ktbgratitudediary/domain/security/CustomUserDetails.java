package ktb.hackathon.ktbgratitudediary.domain.security;

import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(
        Long userId,
        String loginId,
        String password,
        Collection<? extends GrantedAuthority> authorities
) implements UserDetails {
    private static final String AUTHORITY_PREFIX = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.loginId;
    }

    public static CustomUserDetails from(UserDto userDto) {
        return new CustomUserDetails(userDto.id(),
                userDto.loginId(),
                userDto.password(),
                List.of(new SimpleGrantedAuthority(AUTHORITY_PREFIX + userDto.role())));
    }
}
