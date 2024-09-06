package ktb.hackathon.ktbgratitudediary.response;

import org.springframework.http.ResponseEntity;

public class FailureResponse {
    public static ResponseEntity<Object> badRequest(int httpStatus, Object o){
        return ResponseEntity.status(httpStatus).body(o);
    }

    public static ResponseEntity<Object> unAuthorized(int httpStatus, Object o){
        return ResponseEntity.status(httpStatus).body(o);
    }
}
