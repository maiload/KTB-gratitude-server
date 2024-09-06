package ktb.hackathon.ktbgratitudediary.domain;

import ktb.hackathon.ktbgratitudediary.entity.Diary;
import ktb.hackathon.ktbgratitudediary.entity.Template;

import java.time.LocalDateTime;

public record DiaryDto(
        Long id,
        String title,
        String content,
        UserDto userDto,
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
    public static DiaryDto of(
            Long id,
            String title,
            String content,
            UserDto userDto,
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
    ){
        return new DiaryDto(
                null,
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
                createdAt
        );
    }

    public Diary toEntity() {
        return Diary.of(
                id,
                title,
                content,
                userDto.toEntity(),
                happiness,
                weather,
                vectorImage,
                template,
                rType,
                jType,
                mType,
                dType,
                totalDesc,
                totalTitle);
    }

    public static DiaryDto from(Diary diary) {
        return DiaryDto.of(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                UserDto.from(diary.getUser()),
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
}
