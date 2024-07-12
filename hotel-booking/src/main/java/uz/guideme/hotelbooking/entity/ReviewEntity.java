package uz.guideme.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    @Column(name = "rate")
    private int rank;
    private String message;
    private Date createdAt;
    @ManyToOne
    @JoinColumn(name = "hotel_id", foreignKey = @ForeignKey(name = "fk_hotel_review_id"))
    private HotelEntity hotel;
}
