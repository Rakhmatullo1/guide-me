package uz.guideme.bazaar.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Data
@Builder
public class ErrorResponseValidation {
    private int httpStatusCode;
    private List<String> errors;
    private long timestamp;
}