package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.entity.ImageMarketEntity;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.repository.ImageMarketRepository;
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
public class ImageMarketServiceImpl implements ImageService<ImageMarketEntity, MarketEntity> {

    private final ImageClientService imageClientService;
    private final ImageMarketRepository repository;

    @Override
    public Optional<ImageMarketEntity> save(MultipartFile file, MarketEntity entity) {
        log.info("Requested to save new files to db");
        String fileName = imageClientService.uploadFile(file).orElseThrow(()->new InvalidArgumentException("Invalid argument passed"));

        ImageMarketEntity imageMarketEntity = new ImageMarketEntity();

        imageMarketEntity.setMarket(entity);
        imageMarketEntity.setUrl(fileName);

        imageMarketEntity = repository.save(imageMarketEntity);
        log.info("Successfully saved");
        return Optional.of(imageMarketEntity);
    }

    @Override
    public Page<ImageMarketEntity> findByEntity(MarketEntity entity, Pageable pageable) {
        return repository.findAllByMarket(entity, pageable);
    }
}
