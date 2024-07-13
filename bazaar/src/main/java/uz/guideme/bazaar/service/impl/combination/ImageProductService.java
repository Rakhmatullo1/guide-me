package uz.guideme.bazaar.service.impl.combination;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.entity.ImageProductEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.ImageService;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;
import uz.guideme.bazaar.service.impl.ImageProductServiceImpl;

import java.util.*;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Service
@Slf4j
public class ImageProductService {

    private final static String RESPONSE_KEY = "images";

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final ProductService productService;
    private final ImageService imageService;

    public ImageProductService(ProductService productService, ImageProductServiceImpl imageService) {
        this.productService = productService;
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

        ProductEntity product = productService.findByID(UUID.fromString(id));
        List<String> images = new ArrayList<>();

        files.forEach(file->{
            Optional<ImageProductEntity> image = imageService.save(file, product);
            image.ifPresent(v->images.add(serverUrl + "?filePath=" + v.getUrl()));
        });

        return Map.of(RESPONSE_KEY, images);
    }

    public Map<String, List<String>> getAll(String id, int page, int size) {
        log.info("Requested to get images by product");
        ProductEntity product = productService.findByID(UUID.fromString(id));

        Page<ImageProductEntity> images = imageService.findByEntity(product, PageRequest.of(page, size));
        List<String> imageList = images.map(v->serverUrl + "?filePath=" + v.getUrl()).toList();

        return Map.of(RESPONSE_KEY, imageList);
    }
}
