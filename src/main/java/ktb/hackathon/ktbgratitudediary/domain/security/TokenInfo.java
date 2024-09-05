package ktb.hackathon.ktbgratitudediary.domain.security;

public record TokenInfo(
        String tokenType,
        String accessToken,
        String refreshToken
) {
}
