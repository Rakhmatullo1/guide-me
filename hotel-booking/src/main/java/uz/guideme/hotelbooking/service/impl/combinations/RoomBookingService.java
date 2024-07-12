package uz.guideme.hotelbooking.service.impl.combinations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.BookingService;
import uz.guideme.hotelbooking.service.RoomService;
import uz.guideme.hotelbooking.service.dto.BookingDTO;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomBookingService {

    private final RoomService roomService;
    private final BookingService bookingService;

    public Optional<BookingDTO> createRequest(BookingDTO bookingDTO,String token ) {
        log.info("Requested to create request to room. Room ID: {}", bookingDTO.getRoomId());
        RoomEntity room = roomService.findById(bookingDTO.getRoomId());

        return bookingService.create(bookingDTO, room, token);
    }

    public Page<BookingDTO> getBookingByRoomId(UUID roomId, int page, int size) {
        log.info("Requested to get booking by room id: RoomID: {}", roomId);
        RoomEntity room = roomService.findById(roomId);

        return bookingService.getBookingsByRoomID(room, PageRequest.of(page, size));
    }
}
