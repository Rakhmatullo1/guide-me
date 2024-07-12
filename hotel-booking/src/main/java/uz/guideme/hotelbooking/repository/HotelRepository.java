package uz.guideme.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.HotelEntity;

import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity, UUID> {

    boolean existsByLongitudeAndLatitude(Double longitude, Double latitude);

}
