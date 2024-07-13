package uz.guideme.bazaar.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String message;
    private int status;
}
