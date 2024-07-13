package uz.guideme.bazaar.service;

import org.springframework.data.domain.Page;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.dto.CommentDTO;

import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
public interface CommentService {

    Optional<CommentDTO> create(CommentDTO commentDTO, String token, ProductEntity product);

    Page<CommentDTO> findAll(int page, int size, ProductEntity product);

}
