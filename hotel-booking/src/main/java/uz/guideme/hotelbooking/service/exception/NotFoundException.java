package uz.guideme.hotelbooking.service.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{

    private static final int HTTP_STATUS = HttpStatus.NOT_FOUND.value();

    @Override
    public int getStatus() {
        return HTTP_STATUS;
    }

    public NotFoundException(String message) {
        super(message);
    }
}
