package uz.guideme.hotelbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.controller.utils.ResponseUtils;
import uz.guideme.hotelbooking.service.impl.combinations.HotelRoomService;
import uz.guideme.hotelbooking.service.impl.combinations.RoomImageService;
import uz.guideme.hotelbooking.service.RoomService;
import uz.guideme.hotelbooking.service.dto.HotelDTO.RoomDTO;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.mapper.RoomMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final HotelRoomService hotelRoomService;
    private final RoomService roomService;
    private final RoomImageService imageService;

    @PostMapping("/api/hotels/rooms")
    public ResponseEntity<RoomDTO> create(@Valid @RequestBody RoomDTO request, @RequestParam("hotelId") String hotelId)  {
        log.debug("REST request to create new room to hotel. id: {}", hotelId);
        Optional<RoomDTO> result = hotelRoomService.createRoom(request, hotelId);

        ResponseEntity<RoomDTO> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @PostMapping("/api/hotels/rooms/add-facilities")
    public ResponseEntity<Map<String, String>> addFacilities(@RequestBody List<String> facilities, @RequestParam("roomId") String roomId) {
        log.debug("REST request to room facilities. Room ID: {} ", roomId);
        Optional<String> result = roomService.addFacilities(roomId, facilities);

        if(result.isEmpty()) {
            throw new InvalidArgumentException("Invalid argument passed");
        }
        return ResponseEntity.ok(Map.of("message", result.get()));
    }

    @PostMapping("/api/hotels/rooms/images")
    public ResponseEntity<Map<String, List<String>>> uploadPhotos(@RequestParam("roomId") String roomId , @RequestParam("images")List<MultipartFile> files){
        log.debug("REST request to upload photos to room. ID: {}", roomId);
        return ResponseEntity.ok( imageService.upload(files, roomId));
    }

    @GetMapping("/api/hotels/rooms")
    public ResponseEntity<List<RoomDTO>> getRoomsByHotelId(
            @RequestParam(value = "hotelID") String hotelId,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "0") int page) {
        log.debug("REST request to get rooms By hotel id");
        Page<RoomDTO> rooms = hotelRoomService.getRoomsByHotelId(hotelId, PageRequest.of(page, size));

        ResponseEntity<List<RoomDTO>> response = ResponseUtils.wrap(rooms);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/rooms/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable("id") String id) {
        log.debug("REST request to get room By id");
        Optional<RoomDTO> room = Optional.of(RoomMapper.toDto(roomService.findById(UUID.fromString(id)), serverUrl));

        ResponseEntity<RoomDTO> response = ResponseUtils.wrap(room);
        log.debug("Response: {}", response);
        return response;
    }

}
