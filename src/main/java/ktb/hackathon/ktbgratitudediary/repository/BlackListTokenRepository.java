package ktb.hackathon.ktbgratitudediary.repository;

import ktb.hackathon.ktbgratitudediary.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    BlackListToken findByToken(String token);

    Boolean existsByToken(String token);
}