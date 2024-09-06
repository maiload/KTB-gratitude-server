package ktb.hackathon.ktbgratitudediary.domain;

import ktb.hackathon.ktbgratitudediary.entity.Diary;
import ktb.hackathon.ktbgratitudediary.entity.Template;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * DTO for {@link ktb.hackathon.ktbgratitudediary.entity.Diary}
 */
public record DiaryWithEmotionDto(
        Long id,
        String title,
        String content,
        UserDto userDto,
        LinkedHashSet<EmotionEntityDto> emotionEntityDtos,
        int happiness,
        int weather,
        String vectorImage,
        Template template,
        String rType,
        String jType,
        String mType,
        String dType,
        String totalDesc,
        String totalTitle,
        LocalDateTime createdAt
) {
    public static DiaryWithEmotionDto from(Diary diary){
        return new DiaryWithEmotionDto(diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                UserDto.from(diary.getUser()),
                diary.getEmotionEntities().stream()
                        .map(EmotionEntityDto::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                diary.getHappiness(),
                diary.getWeather(),
                diary.getVectorImage(),
                diary.getTemplate(),
                diary.getRType(),
                diary.getJType(),
                diary.getMType(),
                diary.getDType(),
                diary.getTotalDesc(),
                diary.getTotalTitle(),
                diary.getCreatedAt());
    }

    public static DiaryWithEmotionDto of(
                String title,
                String content,
                UserDto userDto,
                LinkedHashSet<EmotionEntityDto> emotionEntityDtos,
                int happiness,
                int weather,
                String vectorImage,
                Template template,
                String rType,
                String jType,
                String mType,
                String dType,
                String totalDesc,
                String totalTitle
    ){
        return new DiaryWithEmotionDto(null,
                title,
                content,
                userDto,
                emotionEntityDtos,
                happiness,
                weather,
                vectorImage,
                template,
                rType,
                jType,
                mType,
                dType,
                totalDesc,
                totalTitle,
                null);
    }

    public DiaryDto toDto() {
        return DiaryDto.of(
                id,
                title,
                content,
                userDto,
                happiness,
                weather,
                vectorImage,
                template,
                rType,
                jType,
                mType,
                dType,
                totalDesc,
                totalTitle,
                createdAt);
    }

}