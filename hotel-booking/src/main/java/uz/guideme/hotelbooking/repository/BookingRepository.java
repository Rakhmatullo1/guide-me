package uz.guideme.hotelbooking.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.guideme.hotelbooking.entity.BookingEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;

import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {
    Page<BookingEntity> findAllByRoom(RoomEntity room, Pageable pageable);

    Page<BookingEntity> findAllByUsername(String username, Pageable pageable);
}
