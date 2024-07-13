package uz.guideme.bazaar.service;

import org.springframework.data.domain.Page;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.service.dto.MarketDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public interface MarketService{

    Optional<MarketDTO> create(MarketDTO marketDTO);

    Page<MarketDTO> getAll(int page, int size);

    Optional<MarketDTO> findByID(UUID id);

    MarketEntity findById(UUID id);

    Optional<MarketDTO> updateMarket(UUID id, MarketDTO marketDTO);

}
