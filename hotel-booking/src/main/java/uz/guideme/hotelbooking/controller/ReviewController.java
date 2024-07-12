package uz.guideme.hotelbooking.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.guideme.hotelbooking.constants.ProjectConstants;
import uz.guideme.hotelbooking.controller.utils.ResponseUtils;
import uz.guideme.hotelbooking.service.impl.combinations.HotelReviewService;
import uz.guideme.hotelbooking.service.ReviewService;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final HotelReviewService hotelReviewService;
    private final ReviewService reviewService;

    @PostMapping("/api/hotels/reviews")
    public ResponseEntity<ReviewDTO> writeReview(
            @Valid @RequestBody ReviewDTO reviewDTO,
            @RequestParam("hotelId") String hotelId,
            @RequestHeader(ProjectConstants.TOKEN_HEADER) String token) {
        log.debug("REST request to write new reviews");
        Optional<ReviewDTO> review = hotelReviewService.createReview(reviewDTO, hotelId, token);

        ResponseEntity<ReviewDTO> response = ResponseUtils.wrap(review);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsByHotelId(
            @RequestParam(value = "hotelID") String hotelId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        log.debug("REST request to get reviews by hotelID");
        Page<ReviewDTO> reviews = hotelReviewService.getReviewsByHotel(hotelId, PageRequest.of(page, size));

        ResponseEntity<List<ReviewDTO>> response = ResponseUtils.wrap(reviews);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/hotels/reviews/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("id") String id) {
        log.debug("REST request to get review by id");
        Optional<ReviewDTO> review = reviewService.findById(UUID.fromString(id));

        ResponseEntity<ReviewDTO> response = ResponseUtils.wrap(review);
        log.debug("Response: {}", response);
        return response;
    }
}
