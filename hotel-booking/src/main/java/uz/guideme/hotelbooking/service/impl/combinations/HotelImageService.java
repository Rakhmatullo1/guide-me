package uz.guideme.hotelbooking.service.impl.combinations;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.ImageHotelsEntity;
import uz.guideme.hotelbooking.service.HotelService;
import uz.guideme.hotelbooking.service.ImageService;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.impl.ImageHotelServiceImpl;
import uz.guideme.hotelbooking.service.mapper.HotelMapper;

import java.util.*;

@Service
@Slf4j
public class HotelImageService {

    private final static String RESPONSE_KEY = "images";

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final HotelService hotelService;
    private final ImageService imageService;

    public HotelImageService(HotelService hotelService, ImageHotelServiceImpl imageService) {
        this.hotelService = hotelService;
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

        HotelEntity hotel = HotelMapper.toEntity(hotelService.findById(UUID.fromString(id)));

        List<String> images = new ArrayList<>();

        files.forEach(file->{
            Optional<ImageHotelsEntity> image =imageService.save(file, hotel);
            image.ifPresent(imageRoomEntity -> images.add(serverUrl + "?filePath=" + imageRoomEntity.getUrl()));
        });

        return Map.of(RESPONSE_KEY, images);
    }
}
