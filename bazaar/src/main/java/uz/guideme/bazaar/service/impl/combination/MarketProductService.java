package uz.guideme.bazaar.service.impl.combination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.service.MarketService;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.dto.ProductDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MarketProductService {

    private final MarketService marketService;
    private final ProductService productService;

    public Optional<ProductDTO> createProduct(UUID marketId, ProductDTO productDTO) {
        log.info("Requested to create new product. ID: {}, Request: {}", marketId, productDTO);
        MarketEntity market = marketService.findById(marketId);
        return productService.create(market, productDTO);
    }

    public Page<ProductDTO> getProducts(UUID marketId, int page, int size){
        log.info("Requested to get products by market");
        MarketEntity market = marketService.findById(marketId);
        return productService.findByMarketById(page, size, market);
    }

}
