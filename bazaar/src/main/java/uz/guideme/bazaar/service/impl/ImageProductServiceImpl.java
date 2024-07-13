package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.entity.ImageProductEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.repository.ImageProductRepository;
import uz.guideme.bazaar.service.ImageService;
import uz.guideme.bazaar.service.client.ImageClientService;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;

import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ImageProductServiceImpl implements ImageService<ImageProductEntity, ProductEntity> {

    private final ImageClientService imageClientService;
    private final ImageProductRepository repository;

    @Override
    public Optional<ImageProductEntity> save(MultipartFile file, ProductEntity entity) {
        log.info("Requested to save new files to db");
        String fileName = imageClientService.uploadFile(file).orElseThrow(()->new InvalidArgumentException("Invalid argument passed"));

        ImageProductEntity image = new ImageProductEntity();

        image.setProduct(entity);
        image.setUrl(fileName);

        image = repository.save(image);
        log.info("Successfully saved");
        return Optional.of(image);
    }

    @Override
    public Page<ImageProductEntity> findByEntity(ProductEntity entity, Pageable pageable) {
        return repository.findAllByProduct(entity, pageable);
    }
}
