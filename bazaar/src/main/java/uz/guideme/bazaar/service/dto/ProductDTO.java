package uz.guideme.bazaar.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.guideme.bazaar.service.enumeration.ProductTypes;

import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Data
public class ProductDTO {
    private UUID id;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotNull(message = "price cannot be null")
    private float price;
    private ProductTypes category;
    private String createdAt;
    private MarketDTO market;
    @NotBlank(message = "description cannot be null")
    private String description;
    private double overallRanking;
    private String imageUrl;

    @Data
    public static class MarketDTO{
        private String imageUrl;
        private String address;
        private String name;
    }
}
