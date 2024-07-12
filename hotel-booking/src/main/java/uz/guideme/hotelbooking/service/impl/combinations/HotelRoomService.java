package uz.guideme.hotelbooking.service.impl.combinations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.service.HotelService;
import uz.guideme.hotelbooking.service.RoomService;
import uz.guideme.hotelbooking.service.dto.HotelDTO;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.mapper.HotelMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelRoomService {

    private final HotelService hotelService;
    private final RoomService roomService;

    public Optional<RoomDTO> createRoom(RoomDTO roomDTO, String hotelId) {
        if(Objects.isNull(hotelId)) {
            log.warn("Hotel ID cannot be null");
            throw new InvalidArgumentException("Hotel ID cannot be null");
        }

        HotelDTO hotelDTO = hotelService.findById(UUID.fromString(hotelId));
        HotelEntity entity = HotelMapper.toEntity(hotelDTO );

        return roomService.create(roomDTO, entity);
    }

    public Page<RoomDTO> getRoomsByHotelId(String hotelId, PageRequest pageRequest){

        if(Objects.isNull(hotelId)) {
            log.warn("Hotel ID cannot be null");
            throw new InvalidArgumentException("Hotel ID cannot be null");
        }

        HotelDTO hotelDTO = hotelService.findById(UUID.fromString(hotelId));
        HotelEntity entity = HotelMapper.toEntity(hotelDTO);

        return roomService.getRoomsByHotel(entity, pageRequest);
    }

}
