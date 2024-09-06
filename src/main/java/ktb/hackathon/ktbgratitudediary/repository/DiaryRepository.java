package ktb.hackathon.ktbgratitudediary.repository;

import ktb.hackathon.ktbgratitudediary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Page<Diary> findByUser_Id(Long userId, Pageable pageable);

    Optional<Diary> findByUser_idAndId(Long userId, Long diaryId);
}