package uz.guideme.hotelbooking.service.mapper;

import uz.guideme.hotelbooking.entity.ReviewEntity;
import uz.guideme.hotelbooking.service.dto.HotelDTO.ReviewDTO;

import java.util.Date;

public class ReviewMapper {
    public static ReviewDTO toDTO(ReviewEntity entity) {
        ReviewDTO dto = new ReviewDTO();

        dto.setId(entity.getId());
        dto.setRank(entity.getRank());
        dto.setMessage(entity.getMessage());
        dto.setCreatedAt(entity.getCreatedAt().toString());
        dto.setUsername(entity.getUsername());

        return dto;
    }

    public static ReviewEntity toEntity(ReviewDTO dto, String username) {
        ReviewEntity entity = new ReviewEntity();

        entity.setCreatedAt(new Date(System.currentTimeMillis()));
        entity.setRank(dto.getRank());
        entity.setMessage(dto.getMessage());
        entity.setId(dto.getId());
        entity.setUsername(username);

        return entity;
    }
}
