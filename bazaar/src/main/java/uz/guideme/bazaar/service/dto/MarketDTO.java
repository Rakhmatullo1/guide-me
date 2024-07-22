package uz.guideme.bazaar.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Data
public class MarketDTO {
    private UUID id;
    @NotBlank(message = "address cannot be blank")
    private String address;
    @NotBlank(message = "address cannot be blank")
    private String name;
    @NotBlank(message = "address cannot be blank")
    private String description;
    @NotNull(message = "longitude cannot be null")
    private double longitude;
    @NotNull(message = "latitude cannot be null")
    private double latitude;
    @NotBlank(message = "address cannot be blank")
    private String city;
    @NotBlank(message = "address cannot be blank")
    @Size(max = 12)
    @Pattern(regexp = "^\\d+$", message = "phone number should be 998XXYYYYYYY")
    private String phoneNumber;
    private double overallRanking;
    private String imageUrl;
}
