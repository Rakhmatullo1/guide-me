package uz.guideme.hotelbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.ReviewEntity;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    Page<ReviewEntity> findAllByHotel(HotelEntity hotel, Pageable pageable);

}
