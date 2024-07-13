package uz.guideme.bazaar.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class InvalidArgumentException extends CustomException{

    private static final int HTTP_STATUS = HttpStatus.BAD_REQUEST.value();

    public InvalidArgumentException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HTTP_STATUS;
    }
}
