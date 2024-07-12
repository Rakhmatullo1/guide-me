package uz.guideme.hotelbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    Page<RoomEntity> findAllByHotelId(HotelEntity hotel, Pageable pageable);

}
