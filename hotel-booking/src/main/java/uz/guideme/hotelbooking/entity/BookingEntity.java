package uz.guideme.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.guideme.hotelbooking.service.enumeration.Status;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "booking")
public class BookingEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private Date createdAt;
    private Date fromDate;
    private Date toDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne()
    @JoinColumn(name = "room_id", foreignKey = @ForeignKey(name = "fk_room_booking_id"))
    private RoomEntity room;
}
