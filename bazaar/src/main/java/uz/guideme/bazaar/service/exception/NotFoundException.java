package uz.guideme.bazaar.service.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class NotFoundException extends CustomException{

    public static final int HTTP_STATUS = HttpStatus.NOT_FOUND.value();

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public int getStatus() {
        return HTTP_STATUS;
    }
}
