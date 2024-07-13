package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.ProductFavoriteEntity;
import uz.guideme.bazaar.repository.ProductFavoriteRepository;
import uz.guideme.bazaar.service.FavoriteService;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.dto.ProductDTO;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;
import uz.guideme.bazaar.service.impl.utils.TokenUtils;
import uz.guideme.bazaar.service.mapper.ProductMapper;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteServiceImpl implements FavoriteService {

    private final ProductFavoriteRepository repository;
    private final ProductService productService;
    @Value("${file.serverUrl}")
    private String serverUrl;

    @Override
    public Optional<Object> add2Favorites(UUID productID, String token) {
        ProductFavoriteEntity entity = new ProductFavoriteEntity();
        String username = TokenUtils.getUsername(token);

        if(repository.existsByUsernameAndProductId(username, productID)) {
            Optional<ProductFavoriteEntity> favorite = repository.findByUsernameAndProductId(username, productID);
            if(favorite.isPresent()){
                repository.delete(favorite.get());
                return Optional.of(Map.of("message", "Successfully removed"));
            }
        }

        entity.setProductId(productID);
        entity.setUsername(username);
        entity = repository.save(entity);
        return Optional.of(ProductMapper.toDto(productService.findByID(entity.getProductId()), serverUrl));
    }

    @Override
    public Page<ProductDTO> getAllFavorites(String token, int page, int size) {
        String username = TokenUtils.getUsername(token);

        if(size<=0 || page<0) {
            log.warn("Page size must not be less than one");
            throw new InvalidArgumentException("Page size or number must not be less than one");
        }

        Page<ProductFavoriteEntity> entities = repository.findAllByUsername(username, PageRequest.of(page, size));

        return entities.map(value -> ProductMapper.toDto(productService.findByID(value.getProductId()), serverUrl));
    }
}
