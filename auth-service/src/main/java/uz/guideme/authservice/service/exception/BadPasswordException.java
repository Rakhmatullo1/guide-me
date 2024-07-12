package uz.guideme.authservice.service.exception;

import org.springframework.http.HttpStatus;

public class BadPasswordException extends CustomException{

    private final static int HTTP_STATUS = HttpStatus.BAD_REQUEST.value();

    public BadPasswordException(String message) {
        super(message);
    }

    public int getStatus() {
        return HTTP_STATUS;
    }
}
