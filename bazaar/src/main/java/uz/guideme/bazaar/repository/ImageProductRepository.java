package uz.guideme.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.ImageProductEntity;
import uz.guideme.bazaar.entity.ProductEntity;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Repository
public interface ImageProductRepository extends JpaRepository<ImageProductEntity, Long> {

    Page<ImageProductEntity> findAllByProduct(ProductEntity product, Pageable pageable);

}
