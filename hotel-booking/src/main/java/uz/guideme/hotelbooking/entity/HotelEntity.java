package uz.guideme.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.guideme.hotelbooking.service.enumeration.HotelType;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotels")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String address;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "hotel_type")
    private HotelType type;
    private String description;
    private double longitude;
    private double latitude;
    private UUID ownerId;
    private String city;
    private String province;
    private int star;
    private String checkIn;
    private String checkOut;
    private String phoneNumber;
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReviewEntity> reviews;
    @OneToMany(mappedBy = "hotelId")
    private List<RoomEntity> rooms;
    @OneToMany(mappedBy = "hotelId")
    private List<ImageHotelsEntity> images;
}
