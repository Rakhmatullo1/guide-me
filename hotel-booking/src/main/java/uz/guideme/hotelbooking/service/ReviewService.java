package uz.guideme.hotelbooking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;

import java.util.Optional;
import java.util.UUID;

public interface ReviewService {
    Optional<ReviewDTO> createReview(ReviewDTO reviewDTO, HotelEntity hotel, String token);

    Page<ReviewDTO> findReviewByHotelId(HotelEntity hotel, PageRequest pageRequest) ;

    Optional<ReviewDTO> findById(UUID reviewID);
}
