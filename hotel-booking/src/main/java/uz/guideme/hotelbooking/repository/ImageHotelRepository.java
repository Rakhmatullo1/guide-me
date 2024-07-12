package uz.guideme.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.ImageHotelsEntity;

import java.util.UUID;

@Repository
public interface ImageHotelRepository extends JpaRepository<ImageHotelsEntity, UUID> {
}
