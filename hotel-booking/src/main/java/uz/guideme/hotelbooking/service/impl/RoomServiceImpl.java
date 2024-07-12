package uz.guideme.hotelbooking.service.impl;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.FacilityEntity;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.repository.FacilitiesRepository;
import uz.guideme.hotelbooking.repository.RoomRepository;
import uz.guideme.hotelbooking.service.RoomService;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.exception.NotFoundException;
import uz.guideme.hotelbooking.service.mapper.RoomMapper;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final RoomRepository roomRepository;
    private final FacilitiesRepository facilitiesRepository;

    @Override
    public Optional<RoomDTO> create(RoomDTO roomDTO, HotelEntity hotel) {
        log.info("Requested to create new room");
        RoomEntity entity = RoomMapper.toEntity(roomDTO);
        entity.setHotelId(hotel);

        entity = roomRepository.save(entity);

        RoomDTO result = RoomMapper.toDto(entity, serverUrl);
        log.info("Successfully created");
        return Optional.of(result);
    }

    @Override
    public Optional<String> addFacilities(String id, List<String> facilities) {
        log.info("Requested to add new data to room. id: {}", id);

        if(Objects.isNull(facilities) && facilities.isEmpty()) {
            log.warn("Facilities list is null or empty");
            throw new InvalidArgumentException("Illegal argument passed. Facilities cannot be null or empty");
        }

        if(StringUtils.isBlank(id)) {
            log.warn("Id is null");
            throw new InvalidArgumentException("Illegal argument passed. Id cannot be null or empty");
        }

        RoomEntity entity = roomRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Room is not found. ID: " + id));

        List<FacilityEntity> facilityEntities = facilities.stream()
                .filter(value -> !facilitiesRepository.existsByNameAndRoomId(value, entity)).map(value -> {
            FacilityEntity facility = new FacilityEntity();
            facility.setName(value);
            facility.setRoomId(entity);
            return facility;
        }).toList();

        facilitiesRepository.saveAll(facilityEntities);
        log.info("Successfully completed");
        return Optional.of("Done!");
    }

    @Override
    public Page<RoomDTO> getRoomsByHotel(HotelEntity entity, PageRequest pageRequest) {
        log.info("Requested to get room by hotel id");

        Page<RoomEntity> rooms = roomRepository.findAllByHotelId(entity, pageRequest);
        return rooms.map(x->{
            RoomDTO dto = RoomMapper.toDto(x, serverUrl);
            dto.setRoomFacilities(new ArrayList<>());
            return dto;
        });
    }

    @Override
    public RoomEntity findById(UUID id) {
        log.info("Requested to find room by id");
        return roomRepository.findById(id).orElseThrow(()->new NotFoundException("Room is not found"));
    }
}
