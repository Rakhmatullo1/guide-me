package uz.guideme.hotelbooking.service.impl.combinations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.service.HotelService;
import uz.guideme.hotelbooking.service.ReviewService;
import uz.guideme.hotelbooking.service.dto.HotelDTO;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;
import uz.guideme.hotelbooking.service.exception.InvalidArgumentException;
import uz.guideme.hotelbooking.service.mapper.HotelMapper;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelReviewService {

    private final ReviewService reviewService;
    private final HotelService hotelService;

    public Optional<ReviewDTO> createReview(ReviewDTO request, String hotelId, String token) {
        if(Objects.isNull(hotelId)) {
            log.warn("Hotel ID is null");
            throw new InvalidArgumentException("invalid argument passed. Hotel ID cannot be null");
        }

        HotelDTO dto = hotelService.findById(UUID.fromString(hotelId));
        return reviewService.createReview(request, HotelMapper.toEntity(dto), token);
    }

    public Page<ReviewDTO> getReviewsByHotel(String id, PageRequest pageRequest) {
        if(Objects.isNull(id)) {
            log.warn("Hotel ID is null");
            throw new InvalidArgumentException("invalid argument passed. Hotel ID cannot be null");
        }

        HotelDTO dto = hotelService.findById(UUID.fromString(id));
        return reviewService.findReviewByHotelId(HotelMapper.toEntity(dto), pageRequest);
    }

}
