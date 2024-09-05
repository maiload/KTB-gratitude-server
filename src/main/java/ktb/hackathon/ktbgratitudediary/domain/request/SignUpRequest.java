package ktb.hackathon.ktbgratitudediary.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import ktb.hackathon.ktbgratitudediary.entity.Role;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record SignUpRequest(

        @NotNull
        @Email
        @Schema(description = "User's login email", example = "user@example.com")
        String loginId,

        @NotNull
        @Schema(description = "User's password", example = "P@ssw0rd!")
        String password,

        @NotNull
        @DateTimeFormat
        @Schema(description = "User's birth date in the format of YYYY-MM-DD", example = "1990-01-01")
        LocalDate birthDate,

        @NotNull
        @Schema(description = "User's nickname", example = "john_doe")
        String nickname
) {
    public UserDto toDto(String encodedPassword) {
        return UserDto.of(loginId, encodedPassword, birthDate, nickname, Role.USER);
    }
}
