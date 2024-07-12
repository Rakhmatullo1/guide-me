package uz.guideme.hotelbooking.service.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailDTO {
    @Size(max = 30000)
    private String content;
}
