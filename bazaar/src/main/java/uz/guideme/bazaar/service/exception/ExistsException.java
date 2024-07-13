package uz.guideme.bazaar.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

public class ExistsException extends CustomException{

    private final static int HTTP_STATUS = HttpStatus.CONFLICT.value();

    public ExistsException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HTTP_STATUS;
    }
}
