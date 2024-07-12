package uz.guideme.hotelbooking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomService {
    Optional<RoomDTO> create(RoomDTO roomDTO, HotelEntity hotel);

    Optional<String> addFacilities(String id, List<String> facilities);

    Page<RoomDTO> getRoomsByHotel(HotelEntity entity, PageRequest pageRequest);

    RoomEntity findById(UUID id);
}
