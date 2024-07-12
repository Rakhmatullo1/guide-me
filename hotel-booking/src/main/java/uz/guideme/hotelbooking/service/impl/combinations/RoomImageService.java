package uz.guideme.hotelbooking.service.impl.combinations;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.entity.ImageRoomEntity;
import uz.guideme.hotelbooking.entity.RoomEntity;
import uz.guideme.hotelbooking.service.ImageService;
import uz.guideme.hotelbooking.service.RoomService;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.impl.ImageRoomServiceImpl;

import java.util.*;

@Service
@Slf4j
public class RoomImageService {

    private final static String RESPONSE_KEY = "images";

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final RoomService roomService;
    private final ImageService imageService;

    public RoomImageService(RoomService roomService, ImageRoomServiceImpl imageService) {
        this.roomService = roomService;
        this.imageService = imageService;
    }

    public Map<String, List<String>> upload(List<MultipartFile> files, String id) {
        if(Objects.isNull(files)|| files.isEmpty()) {
            log.warn("Files cannot be null");
            throw new InvalidArgumentException("Files cannot be null or empty");
        }

        if(StringUtils.isBlank(id)) {
            log.warn("Id is null");
            throw new InvalidArgumentException("Id cannot be empty");
        }

        RoomEntity room = roomService.findById(UUID.fromString(id));

        List<String> images = new ArrayList<>();

        files.forEach(file->{
            Optional<ImageRoomEntity> image =imageService.save(file, room);
            image.ifPresent(imageRoomEntity -> images.add(serverUrl + "?filePath=" + imageRoomEntity.getUrl()));
        });

        return Map.of(RESPONSE_KEY, images);
    }

}
