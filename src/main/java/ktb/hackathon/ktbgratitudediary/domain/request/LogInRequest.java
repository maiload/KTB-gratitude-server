package ktb.hackathon.ktbgratitudediary.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;

public record LogInRequest(

        @NotNull
        @Email
        @Schema(description = "User's login email", example = "user@example.com")
        String loginId,

        @NotNull
        @Schema(description = "User's password", example = "P@ssw0rd!")
        String password
) {
    public UserDto toDto() {
        return UserDto.of(loginId, password);
    }
}
