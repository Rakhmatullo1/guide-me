package uz.guideme.bazaar.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.guideme.bazaar.controller.utils.ResponseUtils;
import uz.guideme.bazaar.service.CommentService;
import uz.guideme.bazaar.service.dto.CommentDTO;
import uz.guideme.bazaar.service.impl.combination.CommentProductService;

import java.util.List;
import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    public final static String TOKEN_HEADER = "X-Token-Header";

    public final CommentService commentService;
    private final CommentProductService productService;

    @PostMapping("/api/market/product/{id}/comment")
    public ResponseEntity<CommentDTO> writeComments(@PathVariable String id,
                                                    @RequestBody CommentDTO commentDTO,
                                                    @RequestHeader(TOKEN_HEADER) String token) {
        log.debug("REST request to write comment");
        Optional<CommentDTO> result = productService.create(commentDTO, id, token);

        ResponseEntity<CommentDTO> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

    @GetMapping("/api/market/product/{id}/comment")
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable String id,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size) {
        log.debug("REST request to get all comments");
        Page<CommentDTO> result = productService.getAllByProduct(id, page, size);

        ResponseEntity<List<CommentDTO>> response = ResponseUtils.wrap(result);
        log.debug("Response: {}", response);
        return response;
    }

}
