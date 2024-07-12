package uz.guideme.fileservice.service.exception;

import org.springframework.http.HttpStatus;

public class InvalidArgumentException extends CustomException{

    private static final int HTTP_STATUS = HttpStatus.BAD_REQUEST.value();

    public InvalidArgumentException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() {
        return super.getHttpStatus();
    }
}
