package uz.guideme.hotelbooking.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.entity.ImageRoomEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.repository.ImageRoomRepository;
import uz.guideme.hotelbooking.service.ImageService;
import uz.guideme.hotelbooking.service.client.ImageClientService;
import uz.guideme.hotelbooking.service.client.impl.ImageClientServiceImpl;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;

import java.util.Optional;

@Service
@Slf4j
public class ImageRoomServiceImpl implements ImageService<ImageRoomEntity, RoomEntity> {

    private final ImageClientService clientService;
    private final ImageRoomRepository imageRoomRepository;

    public ImageRoomServiceImpl(ImageClientServiceImpl clientService, ImageRoomRepository imageRoomRepository) {
        this.clientService = clientService;
        this.imageRoomRepository = imageRoomRepository;
    }

    @Override
    public Optional<ImageRoomEntity> save(MultipartFile image, RoomEntity room) {
        log.info("Requested to save new files to db");
        String fileName = clientService.uploadFile(image).orElseThrow(()->new InvalidArgumentException("Invalid argument passed"));

        ImageRoomEntity entity = new ImageRoomEntity();

        entity.setRoomId(room);
        entity.setUrl(fileName);
        entity = imageRoomRepository.save(entity);

        log.info("Successfully saved new image to room. ID: {}", room.getId());
        return Optional.of(entity);
    }
}
