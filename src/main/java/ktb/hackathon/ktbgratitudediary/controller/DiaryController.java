package ktb.hackathon.ktbgratitudediary.controller;

import ktb.hackathon.ktbgratitudediary.domain.DiaryDto;
import ktb.hackathon.ktbgratitudediary.domain.DiaryWithEmotionDto;
import ktb.hackathon.ktbgratitudediary.domain.UserDto;
import ktb.hackathon.ktbgratitudediary.domain.request.DiaryRequest;
import ktb.hackathon.ktbgratitudediary.domain.response.AiResponse;
import ktb.hackathon.ktbgratitudediary.domain.security.CustomUserDetails;
import ktb.hackathon.ktbgratitudediary.response.SuccessResponse;
import ktb.hackathon.ktbgratitudediary.service.AiService;
import ktb.hackathon.ktbgratitudediary.service.DiaryService;
import ktb.hackathon.ktbgratitudediary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/diaries")
@RequiredArgsConstructor
@Tag(name = "Diary Management", description = "APIs for managing diaries")
public class DiaryController {

    private final UserService userService;
    private final DiaryService diaryService;
    private final AiService aiService;

    @Operation(summary = "일기 전체 조회", description = "Retrieve a paginated list of diaries for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of diaries."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<Object> getDiaries(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<DiaryDto> diaries = diaryService.getDiaries(customUserDetails.userId(), pageable);
        return SuccessResponse.ok(diaries);
    }

    @Operation(summary = "일기 단일 조회", description = "Retrieve details of a specific diary by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the diary."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Diary not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{diaryId}")
    public ResponseEntity<Object> getDiary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("diaryId") Long diaryId
    ) {
        DiaryWithEmotionDto diary = diaryService.getDiary(customUserDetails.userId(), diaryId);
        return SuccessResponse.ok(diary);
    }

    @Operation(summary = "일기 작성", description = "Create a new diary entry for the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created diary."),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    public ResponseEntity<Void> createDiary(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody DiaryRequest diaryRequest
    ) {
        UserDto userDto = userService.getUser(customUserDetails.userId());
        AiResponse aiResponse = aiService.analyzeDiary(diaryRequest.toAiRequest());
        diaryService.saveDiary(aiResponse.toDto(diaryRequest, userDto), aiResponse.emotions());
        return SuccessResponse.created();
    }
}
