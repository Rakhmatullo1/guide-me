package uz.guideme.authservice.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.guideme.authservice.service.dto.response.ErrorDTO;
import uz.guideme.authservice.service.exception.CustomException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final static String EXCEPTION = "Exception: {}";
    private final static String ERROR = "Error {}";

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> customExceptionHandler(CustomException ex) {
        log.warn(EXCEPTION, ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus(ex.getStatus());

        log.warn(ERROR, errorDTO);
        return ResponseEntity.status(ex.getStatus()).body(errorDTO);
    }

}
