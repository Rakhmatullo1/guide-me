package uz.guideme.authservice.service.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{
    private final static int HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public CustomException(String message) {
        super(message);
    }

    public int getStatus() {
        return HTTP_STATUS;
    }
}
