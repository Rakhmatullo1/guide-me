package uz.guideme.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.ProductFavoriteEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Repository
public interface ProductFavoriteRepository extends JpaRepository<ProductFavoriteEntity, Long> {

    Page<ProductFavoriteEntity> findAllByUsername(String username, Pageable pageable);

    boolean existsByUsernameAndProductId(String username, UUID id);

    Optional<ProductFavoriteEntity> findByUsernameAndProductId(String username, UUID id);

}
