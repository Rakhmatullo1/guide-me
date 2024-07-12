package uz.guideme.fileservice.service.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException{

    private static final int HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public CustomException(String message) {
        super(message);
    }

    public int getHttpStatus() {
        return HTTP_STATUS;
    }
}
