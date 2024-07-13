package uz.guideme.bazaar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private float price;
    private String category;
    private Timestamp createdAt;
    private UUID createdBy;
    private String description;
    @ManyToOne
    @JoinColumn(name = "market_id", foreignKey = @ForeignKey(name = "fk_market_product_id"))
    private MarketEntity market;
    @OneToMany(mappedBy = "product")
    public List<ImageProductEntity> images;
    @OneToMany(mappedBy = "product")
    public List<CommentEntity> comments;

}
