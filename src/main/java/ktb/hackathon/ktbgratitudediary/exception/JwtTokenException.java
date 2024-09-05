package ktb.hackathon.ktbgratitudediary.exception;

import lombok.Getter;

@Getter
public class JwtTokenException extends RuntimeException{
    private Error error;

    public JwtTokenException(Exception e) {
        super(e);
    }

    public JwtTokenException(Error error) {
        this.error = error;
    }
}
