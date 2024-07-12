package uz.guideme.authservice.service.dto.response;

import lombok.Data;

@Data
public class ErrorDTO {
    String message;
    int status;
}
