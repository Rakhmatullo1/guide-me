package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.repository.ProductRepository;
import uz.guideme.bazaar.security.SecurityUtils;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.dto.ProductDTO;
import uz.guideme.bazaar.service.enumeration.ProductTypes;
import uz.guideme.bazaar.service.exception.NotFoundException;
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
public class ProductServiceImpl implements ProductService {

    @Value("${file.serverUrl}")
    private String serverUrl;

    private final ProductRepository repository;

    @Override
    public Optional<ProductDTO> create(MarketEntity market, ProductDTO productDTO) {
        log.info("The creating process moved to class. {}", this.getClass());
        ProductEntity entity = ProductMapper.toEntity(productDTO);
        UUID userId = SecurityUtils.getCurrentUserId();

        entity.setCreatedBy(userId);
        entity.setMarket(market);

        entity = repository.save(entity);
        log.info("Successfully finished");
        return Optional.of(ProductMapper.toDto(entity, serverUrl));
    }

    @Override
    public Optional<ProductDTO> findById(UUID id) {
        log.info("Requested to get product by id. ID: {}", id);
        return repository.findById(id).map(value->ProductMapper.toDto(value, serverUrl));
    }

    @Override
    public ProductEntity findByID(UUID id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException("Product is not found"));
    }

    @Override
    public Page<ProductDTO> findAll(int page, int size) {
        log.info("Requested to get all products");
        Page<ProductEntity> products = repository.getAll(page, size) ;
        return products.map(value->ProductMapper.toDto(value, serverUrl));
    }

    @Override
    public Page<ProductDTO> findAllByCategory(int page, int size, ProductTypes category) {
        log.info("Requested to get  products by category");
        Page<ProductEntity> products = repository.getallByCategory(page, size,category.name() );
        return products.map(value->ProductMapper.toDto(value, serverUrl));
    }

    @Override
    public Page<ProductDTO> findByMarketById(int page, int size, MarketEntity market) {
        Page<ProductEntity> products = repository.findAllByMarket(market, PageRequest.of(page, size));
        return products.map(value->ProductMapper.toDto(value, serverUrl));
    }
}
