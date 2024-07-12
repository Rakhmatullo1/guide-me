package uz.guideme.hotelbooking.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.guideme.hotelbooking.entity.HotelEntity;
import uz.guideme.hotelbooking.entity.ReviewEntity;
import uz.guideme.hotelbooking.repository.ReviewRepository;
import uz.guideme.hotelbooking.service.ReviewService;
import uz.guideme.hotelbooking.service.UserService;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;
import uz.guideme.hotelbooking.service.exception.NotFoundException;
import uz.guideme.hotelbooking.service.mapper.ReviewMapper;
import uz.guideme.hotelbooking.service.utils.TokenUtils;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final static String USERNAME_KEY = "username";

    private final ReviewRepository repository;
    private final UserService userService;

    @Override
    public Optional<ReviewDTO> createReview(ReviewDTO reviewDTO, HotelEntity hotel, String token) {
        log.info("Requested to create review");
        String userName = TokenUtils.getUsername(token);
        ReviewEntity review = ReviewMapper.toEntity(reviewDTO, userName);

        review.setHotel(hotel);
        review.setId(UUID.randomUUID());

        review = repository.save(review);
        reviewDTO = ReviewMapper.toDTO(review);

        setUserDetails2Review(reviewDTO, userName);
        return Optional.of(reviewDTO);
    }

    @Override
    public Page<ReviewDTO> findReviewByHotelId(HotelEntity hotel, PageRequest pageRequest) {
        log.info("Requested to find review by hotelID");
        Page<ReviewEntity> reviews = repository.findAllByHotel(hotel, pageRequest);

        return reviews.map(x->{
            ReviewDTO reviewDTO = ReviewMapper.toDTO(x);
            setUserDetails2Review(reviewDTO, x.getUsername());
            return reviewDTO;
        });
    }

    @Override
    public Optional<ReviewDTO> findById(UUID reviewID) {
        ReviewEntity review = repository.findById(reviewID).orElseThrow(()->new NotFoundException("Review not found"));

        ReviewDTO reviewDTO = ReviewMapper.toDTO(review);
        setUserDetails2Review(reviewDTO, review.getUsername());

        return Optional.of(reviewDTO);
    }

    private void setUserDetails2Review(ReviewDTO reviewDTO, String username) {
        Map<String, String> userDetails = userService.getUserInfo(username);
        reviewDTO.setUsername(userDetails.get(USERNAME_KEY));
    }
}
