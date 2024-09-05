package ktb.hackathon.ktbgratitudediary.exception;

import ktb.hackathon.ktbgratitudediary.response.FailureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleBlockedTokenException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return FailureResponse.badRequest(Error.INVALID_INPUT);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleBlockedTokenException(JwtTokenException e) {
        log.error(e.getError().name(), e);
        return FailureResponse.unAuthorized(e.getError());
    }
}
