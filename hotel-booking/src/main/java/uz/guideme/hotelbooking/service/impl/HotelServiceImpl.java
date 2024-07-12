package uz.guideme.hotelbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.repository.HotelRepository;
import uz.guideme.hotelbooking.security.SecurityUtils;
import uz.guideme.hotelbooking.service.HotelService;
import uz.guideme.hotelbooking.service.dto.HotelDTO;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.exception.NotFoundException;
import uz.guideme.hotelbooking.service.mapper.HotelMapper;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final static Map<String, String> RESPONSE =Map.of("message", "deleted!");

    private final HotelRepository hotelRepository;

    @Override
    public Optional<HotelDTO> create(HotelDTO request) {
        log.info("Requested to create new Hotel");
        UUID ownerId = SecurityUtils.getCurrentUserId();

        if(hotelRepository.existsByLongitudeAndLatitude(request.getLongitude(), request.getLatitude())) {
            log.warn("The hotel exists. Change Latitude and longitude");
            throw new InvalidArgumentException("The hotels exists. Change Latitude and longitude");
        }

        HotelEntity entity = HotelMapper.toEntity(request, ownerId);

        entity = hotelRepository.save(entity);
        HotelDTO dto = HotelMapper.toDTO(entity, serverUrl);

        log.info("Successfully created");
        return Optional.of(dto);
    }

    @Override
    public HotelDTO findById(UUID id) {
        log.info("Requested to find by id");
        return HotelMapper.toDTO(getHotel(id), serverUrl);
    }

    @Override
    public Page<HotelDTO> findAllHotels(int size, int num) {
        log.info("Requested to get all hotels");
        Page<HotelEntity> hotels = hotelRepository.findAll(PageRequest.of(num, size));

        return hotels.map(x->{
            HotelDTO dTo = HotelMapper.toDTO(x, serverUrl);
            dTo.setReviews(new ArrayList<>());
            dTo.setRooms(new ArrayList<>());

            return dTo;
        });
    }

    @Override
    public Optional<HotelDTO> update(UUID id, HotelDTO hotelDTO) {
        log.info("Requested to update hotel. ID: {}", id);
        HotelEntity entity = getHotel(id);

        checkUser(entity.getOwnerId());

        if(hotelRepository.existsByLongitudeAndLatitude(hotelDTO.getLongitude(), hotelDTO.getLatitude())) {
            log.warn("The hotel exists. Change Latitude and longitude");
            throw new InvalidArgumentException("The hotels exists. Change Latitude and longitude");
        }

        HotelEntity newEntity = HotelMapper.toEntity(hotelDTO);
        newEntity.setOwnerId(SecurityUtils.getCurrentUserId());
        newEntity.setId(entity.getId());

        newEntity = hotelRepository.save(newEntity);
        log.info("Successfully update data");
        return Optional.of(HotelMapper.toDTO(newEntity, serverUrl));
    }

    @Override
    public Optional<Map<String, String>> delete(UUID id) {
        log.debug("Requested to delete hotel");
        HotelEntity entity = getHotel(id);

        checkUser(entity.getOwnerId());

        entity.setReviews(null);
        entity.setRooms(null);
        entity.setImages(null);

        hotelRepository.delete(entity);
        return Optional.of(RESPONSE);
    }

    private HotelEntity getHotel(UUID id) {
        return hotelRepository.findById(id).orElseThrow(()->new NotFoundException("Hotel is not found " + id.toString()));
    }

    private void checkUser(UUID id) {
        UUID userId = SecurityUtils.getCurrentUserId();

        if(!Objects.equals(userId, id)) {
            log.warn("User id has not matched with user of entity");
            throw new InvalidArgumentException("Only Hotel Owners can update/delete hotels");
        }
    }
}
