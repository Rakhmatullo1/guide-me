package uz.guideme.bazaar.service;

import org.springframework.data.domain.Page;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.dto.ProductDTO;
import uz.guideme.bazaar.service.enumeration.ProductTypes;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public interface ProductService {

    Optional<ProductDTO> create(MarketEntity market, ProductDTO productDTO);

    Optional<ProductDTO> findById(UUID id);

    ProductEntity findByID(UUID id);

    Page<ProductDTO> findAll(int page, int size);

    Page<ProductDTO> findAllByCategory(int page, int size, ProductTypes category);

    Page<ProductDTO> findByMarketById(int page, int size, MarketEntity market);
}
