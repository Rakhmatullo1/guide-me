package uz.guideme.bazaar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.bazaar.controller.utils.ResponseUtils;
import uz.guideme.bazaar.service.MarketService;
import uz.guideme.bazaar.service.dto.MarketDTO;
import uz.guideme.bazaar.service.impl.combination.ImageMarketService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class MarketController {

    private final MarketService marketService;
    private final ImageMarketService imageService;


    @PostMapping("/api/market")
    public ResponseEntity<MarketDTO> create(@RequestBody MarketDTO marketDTO) {
        log.debug("REST request to create a market");
        Optional<MarketDTO> result = marketService.create(marketDTO);

        ResponseEntity<MarketDTO> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market")
    public ResponseEntity<List<MarketDTO>> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
        log.debug("REST request to get all markets");
        Page<MarketDTO> markets = marketService.getAll(page, size);

        ResponseEntity<List<MarketDTO>> response = ResponseUtils.wrap(markets);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/{id}")
    public ResponseEntity<MarketDTO> getById(@PathVariable("id") String id) {
        log.info("REST request to get market by id");
        Optional<MarketDTO> result = marketService.findByID(UUID.fromString(id));

        ResponseEntity<MarketDTO> response = ResponseUtils.wrap(result);
        log.info("Response: {}", response);
        return response;
    }

    @PostMapping("/api/market/{id}/images")
    public ResponseEntity<Map<String, List<String>>> addPhotos(@PathVariable("id") String id, @RequestParam("files")List<MultipartFile> files) {
        log.info("REST request to add images to market");
        Map<String, List<String>>result = imageService.upload(files, id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/api/market/{id}/images")
    public ResponseEntity<Map<String, List<String>>> getImages(@PathVariable("id") String id,
                                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        log.info("REST request to add images to market");
        Map<String, List<String>>result = imageService.getAll(id, page, size);
        return ResponseEntity.ok(result);
    }
}
