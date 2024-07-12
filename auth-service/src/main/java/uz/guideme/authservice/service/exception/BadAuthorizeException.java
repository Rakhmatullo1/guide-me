package uz.guideme.authservice.service.exception;

import org.springframework.http.HttpStatus;

public class BadAuthorizeException extends CustomException{

    private final static int HTTP_STATUS = HttpStatus.CONFLICT.value();

    public BadAuthorizeException(String message) {
        super(message);
    }

    public int getStatus() {
        return HTTP_STATUS;
    }
}
