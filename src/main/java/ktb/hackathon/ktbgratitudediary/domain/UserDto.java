package ktb.hackathon.ktbgratitudediary.domain;

import ktb.hackathon.ktbgratitudediary.entity.Role;
import ktb.hackathon.ktbgratitudediary.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link ktb.hackathon.ktbgratitudediary.entity.User}
 */
public record UserDto(
        Long id,
        String loginId,
        String password,
        LocalDate birthDate,
        String nickname,
        Role role,
        LocalDateTime createdAt
) {

    public static UserDto of(String loginId, String password, LocalDate birthDate, String nickname, Role role) {
        return new UserDto(null, loginId, password, birthDate, nickname, role, null);
    }

    public static UserDto of(String loginId, String password) {
        return new UserDto(null, loginId, password, null, null, null, null);
    }

    public static UserDto from(User user) {
      return new UserDto(user.getId(),
              user.getLoginId(),
              user.getPassword(),
              user.getBirthDate(),
              user.getNickname(),
              user.getRole(),
              user.getCreatedAt());
    }

    public User toEntity() {
       return User.of(loginId, password, birthDate, nickname, role);
    }
}