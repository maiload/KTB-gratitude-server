package ktb.hackathon.ktbgratitudediary.service;

import ktb.hackathon.ktbgratitudediary.domain.request.AiRequest;
import ktb.hackathon.ktbgratitudediary.domain.response.AiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AiService {
    private final RestTemplate template;

    @Value("${Ai.request-url}")
    private String requestUrl;

    public AiResponse analyzeDiary(AiRequest aiRequest) {
        return template.postForObject(requestUrl, aiRequest, AiResponse.class);
    }
}
