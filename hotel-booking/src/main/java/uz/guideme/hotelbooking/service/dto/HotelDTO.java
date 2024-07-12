package uz.guideme.hotelbooking.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import uz.guideme.hotelbooking.service.enumeration.BedType;
import uz.guideme.hotelbooking.service.enumeration.HotelType;

import java.util.List;
import java.util.UUID;

@Data
@JsonIgnoreProperties

public class HotelDTO {
    private UUID id;
    @NotBlank(message = "address cannot be null")
    private String address;
    private HotelType hotelType;
    @NotBlank(message = "name cannot be null")
    private String name;
    private int rank;
    @NotBlank(message = "description cannot be null")
    private String description;
    private List<ReviewDTO> reviews;
    @NotNull(message = "longitude cannot be null")
    private double longitude;
    @NotNull(message = "latitude cannot be null")
    private double latitude;
    @NotNull(message = "city cannot be null")
    private String city;
    @NotNull(message = "province cannot be null")
    private String province;

    private int star;
    @NotNull(message = "checkIn cannot be null")
    private String checkIn;
    @NotNull(message = "checkOut cannot be null")
    private String checkOut;
    @NotNull(message = "phoneNumber cannot be null")
    private String phoneNumber;
    private List<RoomDTO> rooms;
    private UUID ownerId;
    private List<String> images;

    @Data
    public static class ReviewDTO{
        private UUID id;
        private String username;
        @NotBlank(message = "message cannot be null")
        private String message;
        @NotNull(message = "rank cannot be null")
        @Range(min = 0, max = 5, message = "rank should be between 0 and 5")
        private int rank;
        private String createdAt;
    }

    @Data
    public static class RoomDTO{
        private UUID id;
        @NotBlank(message = "rank cannot be null")
        private String name;
        @NotBlank(message = "rank cannot be null")
        private String description;
        @NotNull(message = "size cannot be null")
        private float size;
        @NotNull(message = "hasAC cannot be null")
        private boolean hasAC;
        @NotNull(message = "hasFreeWiFi cannot be null")
        private boolean hasFreeWiFi;
        @NotNull(message = "hasBreakfast cannot be null")
        private boolean hasBreakfast;
        @NotNull(message = "isSmoking cannot be null")
        private boolean isSmoking;

        private List<String> images;
        private List<String> roomFacilities;
        @NotNull
        private BedType bedType;
        @NotNull(message = "bedsCount cannot be null")
        private int bedsCount;
        @NotNull(message = "price cannot be null")
        private double price;
        @NotNull(message = "taxes cannot be null")
        private double taxes;
        @NotNull(message = "breakfastPrice cannot be null")
        private double breakfastPrice;
        @NotNull(message = "maxPerson cannot be null")
        private int maxPerson;
    }
}
