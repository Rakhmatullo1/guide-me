package uz.guideme.bazaar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.guideme.bazaar.entity.CommentEntity;

import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {


    @Query(value = "select * FROM comment WHERE product_id=:prod ORDER BY ratings DESC", nativeQuery = true)
    Page<CommentEntity> getAll(@Param("prod") UUID productId, Pageable pageable);

}
