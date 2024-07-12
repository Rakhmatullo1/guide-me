package uz.guideme.hotelbooking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.dto.BookingDTO;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface BookingService {

    Optional<BookingDTO> create(BookingDTO bookingDTO, RoomEntity room, String token);

    Optional<BookingDTO> getById(UUID id);

    Page<BookingDTO> getBookingsByRoomID(RoomEntity room, Pageable pageable);

    Page<BookingDTO> getBookingsByUsername(String token, Pageable pageable);

    Optional<Map<String, String>> makeDecision(UUID id, boolean isConfirmed);

}
