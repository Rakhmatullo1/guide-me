package uz.guideme.bazaar.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Data
public class CommentDTO {
    private UUID id;
    @NotBlank(message = "message cannot be null")
    private String message;
    @Range(max = 5, min = 0, message = "rating should be between 0 and 5")
    @NotNull(message = "ratings cannot be null")
    private int ratings;
    private Timestamp createdAt;
    private String username;
}
