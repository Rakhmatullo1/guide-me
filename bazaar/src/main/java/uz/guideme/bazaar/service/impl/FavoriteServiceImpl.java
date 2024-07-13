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
import uz.guideme.bazaar.service.impl.utils.TokenUtils;
import uz.guideme.bazaar.service.mapper.ProductMapper;

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
    public Optional<ProductDTO> add2Favorites(UUID productID, String token) {
        ProductFavoriteEntity entity = new ProductFavoriteEntity();

        entity.setProductId(productID);
        entity.setUsername(TokenUtils.getUsername(token));
        entity = repository.save(entity);
        return productService.findById(entity.getProductId());
    }

    @Override
    public Page<ProductDTO> getAllFavorites(String token, int page, int size) {
        String username = TokenUtils.getUsername(token);

        Page<ProductFavoriteEntity> entities = repository.findAllByUsername(username, PageRequest.of(page, size));

        return entities.map(value -> ProductMapper.toDto(productService.findByID(value.getProductId()), serverUrl));
    }
}
