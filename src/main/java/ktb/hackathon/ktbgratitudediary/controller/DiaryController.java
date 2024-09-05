package ktb.hackathon.ktbgratitudediary.controller;

import ktb.hackathon.ktbgratitudediary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;
}
