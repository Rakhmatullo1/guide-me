package uz.guideme.hotelbooking.service;

import org.springframework.data.domain.Page;
import uz.guideme.hotelbooking.service.dto.HotelDTO;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface HotelService {

    Optional<HotelDTO> create(HotelDTO request);

    HotelDTO findById(UUID id);

    Page<HotelDTO> findAllHotels(int size, int num);

    Optional<HotelDTO> update(UUID id, HotelDTO hotelDTO);

    Optional<Map<String, String>> delete(UUID id);
}
