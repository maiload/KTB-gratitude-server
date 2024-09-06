package ktb.hackathon.ktbgratitudediary.domain;

import java.util.List;

public record EmotionEntityWithDescDto(
        String name,
        int per,
        List<String> desc,
        String color,
        DiaryDto diaryDto
) {
    public EmotionEntityDto toDto() {
        return EmotionEntityDto.of(name, per, color, diaryDto);
    }
}
