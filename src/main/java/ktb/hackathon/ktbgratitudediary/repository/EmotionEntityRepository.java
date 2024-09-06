package ktb.hackathon.ktbgratitudediary.repository;

import ktb.hackathon.ktbgratitudediary.entity.EmotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionEntityRepository extends JpaRepository<EmotionEntity, Long> {
}