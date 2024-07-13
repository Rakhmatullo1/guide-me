package uz.guideme.bazaar.service.impl.combination;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.service.CommentService;
import uz.guideme.bazaar.service.ProductService;
import uz.guideme.bazaar.service.dto.CommentDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentProductService {

    private final CommentService commentService;
    private final ProductService productService;

    public Optional<CommentDTO> create(CommentDTO commentDTO, String id, String token){
        log.info("Requested to create new comment in clazz {}", this.getClass().toString());
        ProductEntity product = productService.findByID(UUID.fromString(id));

        return commentService.create(commentDTO, token, product);
    }

    public Page<CommentDTO> getAllByProduct(String id, int page, int size){
        log.debug("Requested to get comments");
        ProductEntity product = productService.findByID(UUID.fromString(id));

        return commentService.findAll(page, size, product);
    }

}
