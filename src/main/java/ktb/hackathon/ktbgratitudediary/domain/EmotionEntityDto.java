package ktb.hackathon.ktbgratitudediary.domain;

import ktb.hackathon.ktbgratitudediary.entity.EmotionEntity;

public record EmotionEntityDto(
        Long id,
        String name,
        int per,
        String color,
        DiaryDto diaryDto
) {
    public static EmotionEntityDto from(EmotionEntity emotionEntity) {
        return new EmotionEntityDto(emotionEntity.getId(),
                emotionEntity.getName(),
                emotionEntity.getPer(),
                emotionEntity.getColor(),
                DiaryDto.from(emotionEntity.getDiary())
        );
    }

    public EmotionEntity toEntity() {
        return EmotionEntity.of(name, per, color, diaryDto.toEntity());
    }

    public static EmotionEntityDto of(
            String name,
            int per,
            String color,
            DiaryDto diaryDto
    ) {
        return new EmotionEntityDto(
                null,
                name,
                per,
                color,
                diaryDto);
    }
}
