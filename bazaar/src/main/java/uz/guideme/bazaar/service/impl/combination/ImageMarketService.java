package uz.guideme.bazaar.service.impl.combination;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.entity.ImageMarketEntity;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.service.ImageService;
import uz.guideme.bazaar.service.MarketService;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;
import uz.guideme.bazaar.service.impl.ImageMarketServiceImpl;

import java.util.*;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */

@Service
@Slf4j
public class ImageMarketService {

    private final static String RESPONSE_KEY = "images";

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final MarketService marketService;
    private final ImageService imageService;

    public ImageMarketService(MarketService marketService, ImageMarketServiceImpl imageService) {
        this.marketService = marketService;
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

        MarketEntity market = marketService.findById(UUID.fromString(id));
        List<String> images = new ArrayList<>();

        files.forEach(file->{
            Optional<ImageMarketEntity> image = imageService.save(file, market);
            image.ifPresent(v->images.add(serverUrl + "?filePath=" + v.getUrl()));
        });

        return Map.of(RESPONSE_KEY, images);
    }

    public Map<String, List<String>> getAll(String id, int page, int size) {
        log.info("Requested to get images by market");

        if(size<=0 || page<0) {
            log.warn("Page size must not be less than one");
            throw new InvalidArgumentException("Page size or number must not be less than one");
        }

        MarketEntity market = marketService.findById(UUID.fromString(id));

        Page<ImageMarketEntity> images = imageService.findByEntity(market, PageRequest.of(page, size));

        List<String> imageList = images.map(v->serverUrl + "?filePath=" + v.getUrl()).toList();
        return Map.of(RESPONSE_KEY, imageList);
    }
}
