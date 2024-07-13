package uz.guideme.bazaar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.guideme.bazaar.controller.utils.ResponseUtils;
import uz.guideme.bazaar.service.FavoriteService;
import uz.guideme.bazaar.service.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class FavoriteController {

    public final static String TOKEN_HEADER = "X-Token-Header";

    private final FavoriteService favoriteService;

    @PostMapping("/api/favorites/product/{id}")
    public ResponseEntity<Object> add2Fav(@PathVariable("id") String id,
                                              @RequestHeader(TOKEN_HEADER) String token) {
        log.debug("REST request add to favorite products");
        Optional<Object> result = favoriteService.add2Favorites(UUID.fromString(id), token);

        ResponseEntity<Object> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/favorites")
    public ResponseEntity<List<ProductDTO>> getFav(@RequestHeader(TOKEN_HEADER) String token,
                                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get favorites");
        Page<ProductDTO> result = favoriteService.getAllFavorites(token, page, size);

        ResponseEntity<List<ProductDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

}
