package uz.guideme.hotelbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.controller.utils.ResponseUtils;
import uz.guideme.hotelbooking.service.HotelService;
import uz.guideme.hotelbooking.service.dto.HotelDTO;
import uz.guideme.hotelbooking.service.impl.combinations.HotelImageService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final static String RESPONSE_MSG = "Response: {}";

    private final HotelImageService imageService;
    private final HotelService hotelService;

    @PostMapping("/api/hotels")
    public ResponseEntity<HotelDTO> create(@Valid @RequestBody HotelDTO hotelDTO) {
        log.debug("REST request to create new hotel");
        Optional<HotelDTO> result = hotelService.create(hotelDTO);

        ResponseEntity<HotelDTO> response = ResponseUtils.wrap(result);
        log.debug(RESPONSE_MSG, response);
        return response;
    }

    @GetMapping("/api/hotels")
    public ResponseEntity<List<HotelDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        log.debug("REST request to get all hotels");
        Page<HotelDTO> result = hotelService.findAllHotels(size, page);

        ResponseEntity<List<HotelDTO>> response = ResponseUtils.wrap(result);

        log.debug("Response size: {}", Objects.requireNonNull(response.getBody()).size());
        return response;
    }

    @PostMapping("/api/hotels/images")
    public ResponseEntity<Map<String, List<String>>> uploadPhotos(@RequestParam("hotelId") String id , @RequestParam("images")List<MultipartFile> files){
        log.debug("REST request to upload photos to room. ID: {}", id);
        return ResponseEntity.ok( imageService.upload(files, id));
    }

    @GetMapping("/api/hotels/{id}")
    public ResponseEntity<HotelDTO> getById(@PathVariable String id) {
        log.debug("REST request to get hotel by id");
        HotelDTO result = hotelService.findById(UUID.fromString(id));

        ResponseEntity<HotelDTO> response = ResponseUtils.wrap(Optional.of(result));
        log.debug(RESPONSE_MSG, response);
        return response;
    }

    @PutMapping("/api/hotels/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable String id, @RequestBody HotelDTO hotelDTO) {
        log.debug("REST request to update hotel. ID: {}", id);
        Optional<HotelDTO> result = hotelService.update(UUID.fromString(id), hotelDTO);

        ResponseEntity<HotelDTO> response = ResponseUtils.wrap(result);
        log.debug("Successfully updated");
        return response;
    }

    @DeleteMapping("/api/hotels/{id}")
    public ResponseEntity<Map<String, String>> deleteHotel(@PathVariable String id) {
        log.debug("REST request to delete hotel. ID: {}", id);
        Optional<Map<String, String>> result = hotelService.delete(UUID.fromString(id));

        ResponseEntity<Map<String, String>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }
}