package uz.guideme.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.ImageRoomEntity;

import java.util.UUID;

@Repository
public interface ImageRoomRepository extends JpaRepository<ImageRoomEntity, UUID> {
}
