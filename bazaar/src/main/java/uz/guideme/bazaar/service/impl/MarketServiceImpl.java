package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.MarketEntity;
import uz.guideme.bazaar.repository.MarketRepository;
import uz.guideme.bazaar.security.SecurityUtils;
import uz.guideme.bazaar.service.MarketService;
import uz.guideme.bazaar.service.dto.MarketDTO;
import uz.guideme.bazaar.service.exception.CustomException;
import uz.guideme.bazaar.service.exception.ExistsException;
import uz.guideme.bazaar.service.exception.InvalidArgumentException;
import uz.guideme.bazaar.service.exception.NotFoundException;
import uz.guideme.bazaar.service.mapper.MarketMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MarketServiceImpl implements MarketService {

    @Value("${file.serverUrl}")
    private String host;

    private final MarketRepository marketRepository;

    @Override
    public Optional<MarketDTO> create(MarketDTO marketDTO) {
        log.info("Requested to create new market. Request: {}", marketDTO);
        MarketEntity entity = MarketMapper.toEntity(marketDTO);

        if(marketRepository.existsByLongitudeAndLatitude(entity.getLongitude(), entity.getLatitude())){
            log.warn("Market exists by longitude and latitude");
            throw new ExistsException(String.format("Market exists by longitude and latitude. Longitude: %s, Latitude: %s", entity.getLongitude(), entity.getLatitude()));
        }

        UUID userID = SecurityUtils.getCurrentUserId();

        entity = marketRepository.save(entity);
        entity.setOwnerId(userID);

        log.info("Successfully created new market");
        return Optional.of(MarketMapper.toDto(entity, host));
    }

    @Override
    public Page<MarketDTO> getAll(int page, int size) {
        log.info("Requested to get all markets");

        if(size<=0 || page<0) {
            log.warn("Page size must not be less than one");
            throw new InvalidArgumentException("Page size or number must not be less than one");
        }

        Page<MarketEntity> markets = marketRepository.findAll(PageRequest.of(page, size));

        return markets.map(market -> MarketMapper.toDto(market, host));
    }

    @Override
    public Optional<MarketDTO> findByID(UUID id) {
        log.info("Requested to get market dto by id");
        Optional<MarketEntity> market = marketRepository.findById(id);
        return market.map(v-> MarketMapper.toDto(v, host));
    }

    @Override
    public MarketEntity findById(UUID id) {
        log.info("Requested to get market entity by id {}", id);
        return marketRepository.findById(id).orElseThrow(()-> new NotFoundException("Market is not found. ID: "+ id));
    }

    @Override
    public Optional<MarketDTO> updateMarket(UUID id, MarketDTO marketDTO) {
        log.info("Requested to update market. ID {}", id);
        MarketEntity market = findById(id);

        if(!Objects.equals(market.getOwnerId(), SecurityUtils.getCurrentUserId())) {
            log.warn("User have not permission to update market");
            throw new CustomException("You does not have permission to update");
        }

        market = MarketMapper.toEntity(marketDTO);
        market.setId(id);
        market = marketRepository.save(market);

        return Optional.of( MarketMapper.toDto(market, host));
    }
}
