package uz.guideme.hotelbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.ImageHotelsEntity;
import uz.guideme.hotelbooking.repository.ImageHotelRepository;
import uz.guideme.hotelbooking.service.ImageService;
import uz.guideme.hotelbooking.service.client.ImageClientService;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageHotelServiceImpl implements ImageService<ImageHotelsEntity, HotelEntity> {

    private final ImageClientService clientService;
    private final ImageHotelRepository repository;

    @Override
    public Optional<ImageHotelsEntity> save(MultipartFile file, HotelEntity hotel) {
        log.info("Requested to save new files to db");
        String fileName = clientService.uploadFile(file).orElseThrow(()->new InvalidArgumentException("Invalid argument passed"));

        ImageHotelsEntity entity = new ImageHotelsEntity();

        entity.setHotelId(hotel);
        entity.setUrl(fileName);
        entity = repository.save(entity);

        log.info("Successfully saved new image to room. ID: {}", hotel.getId());
        return Optional.of(entity);
    }
}
