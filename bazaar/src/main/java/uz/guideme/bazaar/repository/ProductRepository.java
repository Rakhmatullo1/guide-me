package uz.guideme.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.entity.ProductEntity;

import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT  * FROM product ORDER BY created_at LIMIT :size OFFSET (:size * :page) ", nativeQuery = true)
    Page<ProductEntity> getAll(@Param("page") int page, @Param("size") int size);

    @Query(value = "SELECT  * FROM product WHERE category = :category ORDER BY created_at LIMIT :size OFFSET (:size * :page)", nativeQuery = true)
    Page<ProductEntity> getallByCategory(@Param("page") int page, @Param("size") int size,@Param("category") String category);

    Page<ProductEntity> findAllByMarket(MarketEntity market, Pageable pageable);
}
