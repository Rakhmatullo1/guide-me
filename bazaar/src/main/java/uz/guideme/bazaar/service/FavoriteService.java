package uz.guideme.bazaar.service;

import org.springframework.data.domain.Page;
import uz.guideme.bazaar.service.dto.ProductDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

public interface FavoriteService {

    Optional<Object> add2Favorites(UUID product, String token);

    Page<ProductDTO> getAllFavorites(String token, int page, int size);

}
