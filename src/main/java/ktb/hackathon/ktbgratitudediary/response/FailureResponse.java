package ktb.hackathon.ktbgratitudediary.response;

import ktb.hackathon.ktbgratitudediary.exception.Error;
import org.springframework.http.ResponseEntity;

public class FailureResponse {
    public static ResponseEntity<Object> badRequest(Error error){
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }

    public static ResponseEntity<Object> unAuthorized(Error error){
        return ResponseEntity.status(error.getHttpStatus()).body(error);
    }
}
