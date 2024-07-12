package uz.guideme.hotelbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.BookingEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.repository.BookingRepository;
import uz.guideme.hotelbooking.service.BookingService;
import uz.guideme.hotelbooking.service.UserService;
import uz.guideme.hotelbooking.service.dto.BookingDTO;
import uz.guideme.hotelbooking.service.enumeration.Status;
import uz.guideme.hotelbooking.service.exception.NotFoundException;
import uz.guideme.hotelbooking.service.mapper.BookingMapper;
import uz.guideme.hotelbooking.service.utils.TokenUtils;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;

    @Override
    public Optional<BookingDTO> create(BookingDTO bookingDTO, RoomEntity room, String token) {
        log.info("Requested to create booking");
        String username = getUsername(token);
        String email = getEmail(username);

        BookingEntity entity = BookingMapper.toEntity(bookingDTO, room, username );
        entity = bookingRepository.save(entity);

        log.info("Successfully saved");
        return Optional.of(BookingMapper.toDto(entity, email));
    }

    @Override
    public Optional<BookingDTO> getById(UUID id) {
        log.info("Requested to get booking by id");
        BookingEntity entity = getEntity(id);
        String email = getEmail(entity.getUsername());
        return Optional.of(BookingMapper.toDto(entity,email ));
    }

    @Override
    public Page<BookingDTO> getBookingsByRoomID(RoomEntity room, Pageable pageable) {
        Page<BookingEntity> bookings = bookingRepository.findAllByRoom(room, pageable);

        return bookings.map(b->{
            String email = getEmail(b.getUsername());
            return BookingMapper.toDto(b, email);
        });
    }

    @Override
    public Page<BookingDTO> getBookingsByUsername(String token, Pageable pageable) {
        log.info("Requested to get bookings by username");
        String username = getUsername(token);

        Page<BookingEntity> entities = bookingRepository.findAllByUsername(username, pageable);
        return entities.map(b->{
            String email = getEmail(b.getUsername());
            return BookingMapper.toDto(b, email);
        });
    }

    @Override
    public Optional<Map<String, String>> makeDecision(UUID id, boolean isConfirmed) {
        log.info("Requested to confirm/reject booking");
        BookingEntity entity = getEntity(id);
        entity.setStatus(Status.REJECTED);
        if(isConfirmed) {
            entity.setStatus(Status.ACCEPTED);
        }

        bookingRepository.save(entity);

        return Optional.of(Map.of("message", "Successfully updated"));
    }

    private BookingEntity getEntity(UUID id) {
        return bookingRepository.findById(id).orElseThrow(()->new NotFoundException("Booking is not found"));
    }

    private Map<String, String> getUserDataByToken(String token) {
        return userService.getUserInfo(TokenUtils.getUsername(token));
    }

    private Map<String, String> getUserDataByUsername(String username) {
        return userService.getUserInfo(username);
    }

    private String getUsername(String token) {
        return Optional.ofNullable(getUserDataByToken(token).get("username")).orElseThrow(()->new NotFoundException("Username is not found in token"));
    }

    private String getEmail(String username) {
        return Optional.ofNullable(getUserDataByUsername(username).get("email")).orElseThrow(()->new NotFoundException("Email is not found in token"));
    }


}
