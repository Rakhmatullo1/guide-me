package uz.guideme.hotelbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.FacilityEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;

import java.util.UUID;

@Repository
public interface FacilitiesRepository extends JpaRepository<FacilityEntity, UUID> {

    boolean existsByNameAndRoomId(String name, RoomEntity entity);
}
