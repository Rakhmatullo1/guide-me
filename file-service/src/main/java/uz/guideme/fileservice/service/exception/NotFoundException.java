package uz.guideme.fileservice.service.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    private static final int HTTP_STATUS = HttpStatus.NOT_FOUND.value();

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public int getHttpStatus() {
        return super.getHttpStatus();
    }
}
