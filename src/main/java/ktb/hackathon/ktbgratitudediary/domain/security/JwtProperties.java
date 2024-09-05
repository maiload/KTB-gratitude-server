package ktb.hackathon.ktbgratitudediary.domain.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties("jwt")
public record JwtProperties(
        String secretKey,
        Duration accessExpire,
        Duration refreshExpire
) {
}
