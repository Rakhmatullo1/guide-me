package uz.guideme.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.guideme.hotelbooking.service.enumeration.BedType;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "rooms")
public class RoomEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private float size;
    @Column(name = "has_AC")
    private boolean hasAC;
    @Column(name = "has_free_wifi")
    private boolean hasFreeWifi;
    private boolean isSmoking;
    @Enumerated(EnumType.STRING)
    private BedType bedType;
    private Double price;
    private Double taxes;
    private boolean hasBreakfast;
    @ManyToOne
    @JoinColumn(name = "hotel_id",foreignKey = @ForeignKey(name = "fk_hotel_room_id"))
    private HotelEntity hotelId;
    @OneToMany(mappedBy = "room")
    private List<BookingEntity> bookings;
    private int bedsCount;
    @OneToMany(mappedBy = "roomId")
    private List<ImageRoomEntity> images;
    @OneToMany(mappedBy = "roomId")
    private List<FacilityEntity> facilities;
    @Column(name = "breakfast_price")
    private Double breakfastPrice;
    @Column(name = "max_person")
    private int maxPerson;
}
