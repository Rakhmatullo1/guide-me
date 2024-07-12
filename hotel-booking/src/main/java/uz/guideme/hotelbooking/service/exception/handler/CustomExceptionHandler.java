package uz.guideme.hotelbooking.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import uz.guideme.hotelbooking.service.dto.ErrorDTO;
import uz.guideme.hotelbooking.service.dto.ErrorResponseValidation;
import uz.guideme.hotelbooking.service.exception.CustomException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    private final static String EXCEPTION = "Exception: {}";
    private final static String ERROR = "Error: {}";


    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleExceptionHandler(CustomException exception) {
        log.warn(EXCEPTION, exception.getMessage());
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(exception.getMessage());
        errorDTO.setStatus(exception.getStatus());

        log.warn(ERROR, errorDTO);
        return ResponseEntity.status(exception.getStatus()).body(errorDTO);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseValidation> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ErrorResponseValidation errors = ErrorResponseValidation
                .builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(System.currentTimeMillis())
                .build();
        List<String> errorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorList.add(String.format("%s: %s", fieldName, errorMessage));
        });
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        log.warn(EXCEPTION, ex.getMessage());
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(ex.getMessage());
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());

        log.warn(ERROR, errorDTO);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorDTO);
    }
}
