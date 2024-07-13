package uz.guideme.bazaar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.controller.utils.ResponseUtils;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.dto.ProductDTO;
import uz.guideme.bazaar.service.enumeration.ProductTypes;
import uz.guideme.bazaar.service.impl.combination.ImageProductService;
import uz.guideme.bazaar.service.impl.combination.MarketProductService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final MarketProductService marketProductService;
    private final ProductService productService;
    private final ImageProductService imageProductService;

    @PostMapping("/api/market/{id}/product")
    public ResponseEntity<ProductDTO> createProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        log.debug("REST request to add new product");
        Optional<ProductDTO> result = marketProductService.createProduct(UUID.fromString(id), productDTO);

        ResponseEntity<ProductDTO> response = ResponseUtils.wrap(result);
        log.info("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/{id}/product")
    public ResponseEntity<List<ProductDTO>> getProductsByMarketId(@PathVariable String id, @RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("REST request to get products");
        Page<ProductDTO> result = marketProductService.getProducts(UUID.fromString(id), page, size);

        ResponseEntity<List<ProductDTO>> response = ResponseUtils.wrap(result);
        log.info("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/product")
    public ResponseEntity<List<ProductDTO>> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get all products");
        Page<ProductDTO> result = productService.findAll(page, size);

        ResponseEntity<List<ProductDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/product/category/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(
            @PathVariable ProductTypes category,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get all products");
        Page<ProductDTO> result = productService.findAllByCategory(page, size, category);

        ResponseEntity<List<ProductDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/product/{id}")
    public ResponseEntity<ProductDTO> getById(@PathVariable String id) {
        log.debug("REST request to get product by id");
        Optional<ProductDTO> product = productService.findById(UUID.fromString(id));

        ResponseEntity<ProductDTO> response = ResponseUtils.wrap(product);
        log.debug("Response: {}", response);
        return response;
    }

    @PostMapping("/api/market/product/{id}/image")
    public ResponseEntity<Map<String, List<String>>> uploadImages(@PathVariable String id, @RequestParam("files") List<MultipartFile> files) {
        log.debug("REST request to upload images");
        Map<String, List<String>> result = imageProductService.upload(files, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/market/product/{id}/image")
    public ResponseEntity<Map<String, List<String>>> getImages(@PathVariable String id,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to upload images");
        Map<String, List<String>> result = imageProductService.getAll(id, page, size);
        return ResponseEntity.ok(result);
    }
}

