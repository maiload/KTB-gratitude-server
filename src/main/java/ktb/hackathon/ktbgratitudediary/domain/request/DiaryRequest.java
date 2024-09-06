package ktb.hackathon.ktbgratitudediary.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ktb.hackathon.ktbgratitudediary.entity.Template;

public record DiaryRequest(
        @Schema(description = "Title of the diary", example = "A beautiful day")
        @NotNull
        String title,

        @Schema(description = "Content of the diary", example = "Today I went for a walk and enjoyed the sunny weather...")
        @NotNull
        String content,

        @Schema(description = "Weather condition when the diary was written", example = "701")
        int weather,

        @Schema(description = "Base64 encoded vector image", example = "data:image/svg+xml;base64,...")
        String vectorImage,

        @Schema(description = "Template type for the diary", example = "DEFAULT")
        @NotNull
        Template template
) {
    public AiRequest toAiRequest() {
        return new AiRequest(content);
    }
}

