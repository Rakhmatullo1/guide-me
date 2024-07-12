package uz.guideme.hotelbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.guideme.hotelbooking.constants.ProjectConstants;
import uz.guideme.hotelbooking.controller.utils.ResponseUtils;
import uz.guideme.hotelbooking.service.BookingService;
import uz.guideme.hotelbooking.service.dto.BookingDTO;
import uz.guideme.hotelbooking.service.impl.combinations.RoomBookingService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final RoomBookingService roomBookingService;

    private final BookingService bookingService;

    @PostMapping("/api/hotels/rooms/bookings")
    public ResponseEntity<BookingDTO> create(
            @Valid @RequestBody BookingDTO dto,
            @RequestHeader(ProjectConstants.TOKEN_HEADER) String token) {
        log.debug("REST request to create new booking");
        Optional<BookingDTO> result = roomBookingService.createRequest(dto, token);

        ResponseEntity<BookingDTO> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/rooms/{id}/bookings")
    public ResponseEntity<List<BookingDTO>> getByRoomId(
            @PathVariable("id") String id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get bookings by room id");
        Page<BookingDTO> result = roomBookingService.getBookingByRoomId(UUID.fromString(id), page, size);

        ResponseEntity<List<BookingDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/rooms/bookings")
    public ResponseEntity<List<BookingDTO>> getByUsername(
            @RequestHeader(ProjectConstants.TOKEN_HEADER) String token,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get bookings by username");
        Page<BookingDTO> result = bookingService.getBookingsByUsername(token, PageRequest.of(page, size));

        ResponseEntity<List<BookingDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/rooms/bookings/{id}")
    public ResponseEntity<BookingDTO> getByID(@PathVariable("id") String id) {
        log.debug("REST request to get booking by booking");
        Optional<BookingDTO> result = bookingService.getById(UUID.fromString(id));

        ResponseEntity<BookingDTO> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @PutMapping("/api/hotels/rooms/bookings/{id}/make-decision")
    public ResponseEntity<Map<String, String>> makeDecision(@PathVariable("id") String id,
                                                            @RequestParam("isConfirmed") boolean isConfirmed) {
        log.debug("REST request to get booking by booking");
        Optional<Map<String, String>> result = bookingService.makeDecision(UUID.fromString(id), isConfirmed);

        ResponseEntity<Map<String, String>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }
}
