package uz.guideme.bazaar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "market")
public class MarketEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String address;
    private String name;
    private String description;
    private double longitude;
    private double latitude;
    private UUID ownerId;
    private String city;
    private String phoneNumber;
    @OneToMany(mappedBy = "market")
    private List<ProductEntity> products;
    @OneToMany(mappedBy = "market")
    private List<ImageMarketEntity> images;
}
