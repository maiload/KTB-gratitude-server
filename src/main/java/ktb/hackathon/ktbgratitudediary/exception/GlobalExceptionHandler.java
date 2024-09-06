package ktb.hackathon.ktbgratitudediary.exception;

import ktb.hackathon.ktbgratitudediary.response.FailureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleBlockedTokenException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        Error error = Error.INVALID_INPUT;
        return FailureResponse.badRequest(error.getHttpStatus(), alterEnumToResponse(error));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleBlockedTokenException(JwtTokenException e) {
        Error error = e.getError();
        log.error(error.name(), e);
        return FailureResponse.unAuthorized(error.getHttpStatus(), alterEnumToResponse(error));
    }

    private Map<String, Object> alterEnumToResponse(Error error) {
        Map<String, Object> response = new HashMap<>();
        response.put("detailCode", error.getDetailCode());
        response.put("message", error.name());
        return response;
    }
}
