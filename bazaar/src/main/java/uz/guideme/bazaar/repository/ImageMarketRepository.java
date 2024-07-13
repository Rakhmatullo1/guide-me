package uz.guideme.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.ImageMarketEntity;
import uz.guideme.bazaar.entity.MarketEntity;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Repository
public interface ImageMarketRepository extends JpaRepository<ImageMarketEntity, Long> {

    Page<ImageMarketEntity> findAllByMarket(MarketEntity entity, Pageable pageable);

}
