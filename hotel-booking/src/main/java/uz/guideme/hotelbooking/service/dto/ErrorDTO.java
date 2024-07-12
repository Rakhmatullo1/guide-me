package uz.guideme.hotelbooking.service.dto;

import lombok.Data;

@Data
public class ErrorDTO {
    private String message;
    private int status;
}
