package ktb.hackathon.ktbgratitudediary.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;

public record LogInRequest(

        @Schema(description = "User's login email", example = "user@example.com")
        String loginId,

        @Schema(description = "User's password", example = "P@ssw0rd!")
        String password
) {
    public UserDto toDto() {
        return UserDto.of(loginId, password);
    }
}
