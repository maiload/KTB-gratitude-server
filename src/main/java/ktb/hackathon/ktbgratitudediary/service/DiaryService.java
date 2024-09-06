package ktb.hackathon.ktbgratitudediary.service;

import jakarta.persistence.EntityNotFoundException;
import ktb.hackathon.ktbgratitudediary.domain.DiaryDto;
import ktb.hackathon.ktbgratitudediary.domain.DiaryWithEmotionDto;
import ktb.hackathon.ktbgratitudediary.domain.EmotionEntityDto;
import ktb.hackathon.ktbgratitudediary.domain.response.AiResponse;
import ktb.hackathon.ktbgratitudediary.entity.Diary;
import ktb.hackathon.ktbgratitudediary.entity.EmotionEntity;
import ktb.hackathon.ktbgratitudediary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public Page<DiaryDto> getDiaries(Long userId, Pageable pageable) {
        return diaryRepository.findByUser_Id(userId, pageable).map(DiaryDto::from);
    }

    public DiaryWithEmotionDto getDiary(Long userId, Long diaryId) {
        return diaryRepository.findByUser_idAndId(userId, diaryId)
                .map(DiaryWithEmotionDto::from)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void saveDiary(DiaryDto diaryDto, List<AiResponse.Emotion> emotions) {
        Diary diary = diaryDto.toEntity();

        LinkedHashSet<EmotionEntity> emotionEntities = emotions.stream()
                .map(emotion -> emotion.toDto(diaryDto))
                .map(emotionEntityWithDescDto -> {
                    EmotionEntityDto emotionEntityDto = emotionEntityWithDescDto.toDto();
                    EmotionEntity emotionEntity = emotionEntityDto.toEntity();
                    emotionEntity.addDesc(emotionEntityWithDescDto.desc());
                    emotionEntity.addDiary(diary);
                    return emotionEntity;
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));

        diary.addEmotionEntities(emotionEntities);
        diaryRepository.save(diary);
    }

}
