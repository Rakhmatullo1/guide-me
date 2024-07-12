package uz.guideme.hotelbooking.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import uz.guideme.hotelbooking.constants.ProjectConstants;
import uz.guideme.hotelbooking.service.enumeration.Status;

import java.util.UUID;

@Data
public class BookingDTO {
    private UUID id;
    private String username;
    private String email;
    @NotNull(message = "roomId cannot be null or blank")
    private UUID roomId;
    private String createdAt;
    @Pattern(regexp = ProjectConstants.PATTERN_FOR_DATE, message = "Date should be in YYYY-MM-DD format")
    @NotNull(message = "fromDate cannot be null or blank")
    private String fromDate;
    @Pattern(regexp = ProjectConstants.PATTERN_FOR_DATE, message = "Date should be in YYYY-MM-DD format")
    @NotNull(message = "toDate cannot be null or blank")
    private String toDate;
    private Status status;
}
