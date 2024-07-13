package uz.guideme.bazaar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.MarketEntity;

import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Repository
public interface MarketRepository extends JpaRepository<MarketEntity, UUID> {

    boolean existsByLongitudeAndLatitude(Double longitude, Double latitude);

}
