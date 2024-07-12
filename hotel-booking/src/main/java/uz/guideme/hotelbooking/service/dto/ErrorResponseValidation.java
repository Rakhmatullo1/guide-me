package uz.guideme.hotelbooking.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponseValidation {
    private int httpStatusCode;
    private List<String> errors;
    private long timestamp;
}
