package uz.guideme.bazaar.service.mapper;

import uz.guideme.bazaar.entity.CommentEntity;
import uz.guideme.bazaar.service.dto.CommentDTO;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
public class CommentMapper {

    public static CommentDTO toDto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();

        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setRatings(entity.getRatings());
        dto.setUsername(entity.getUsername());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }

}
