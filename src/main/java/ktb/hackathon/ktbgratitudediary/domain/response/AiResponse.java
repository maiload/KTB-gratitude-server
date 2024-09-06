package ktb.hackathon.ktbgratitudediary.domain.response;

import ktb.hackathon.ktbgratitudediary.domain.DiaryDto;
import ktb.hackathon.ktbgratitudediary.domain.EmotionEntityWithDescDto;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import ktb.hackathon.ktbgratitudediary.domain.request.DiaryRequest;

import java.util.List;

public record AiResponse(
        List<Emotion> emotions,
        Happy happy,
        RJMD rjmd
) {
    public DiaryDto toDto(DiaryRequest diaryRequest, UserDto userDto) {
        return DiaryDto.of(
                null,
                diaryRequest.title(),
                diaryRequest.content(),
                userDto,
                happy.per,
                diaryRequest.weather(),
                diaryRequest.vectorImage(),
                diaryRequest.template(),
                rjmd.R.toString(),
                rjmd.J.toString(),
                rjmd.M.toString(),
                rjmd.D.toString(),
                rjmd.desc,
                rjmd.title,
                null
        );
    }

    public record Emotion(
            String name,
            int per,
            List<String> desc,
            String color
    ) {
        public EmotionEntityWithDescDto toDto(DiaryDto diaryDto) {
            return new EmotionEntityWithDescDto(name, per, desc, color, diaryDto);
        }
    }

    public record Happy(
            int per
    ) {}

    public record RJMD(
            RJMDEntry R,
            RJMDEntry J,
            RJMDEntry M,
            RJMDEntry D,
            String desc,
            String title
    ) {
        public record RJMDEntry(
                String type,
                int per,
                String desc
        ) {
            @Override
            public String toString() {
                return type + ", " + per + ", " + desc;
            }
        }
    }
}

