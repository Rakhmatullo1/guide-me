package uz.guideme.bazaar.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.guideme.bazaar.entity.CommentEntity;
import uz.guideme.bazaar.entity.ProductEntity;
import uz.guideme.bazaar.repository.CommentRepository;
import uz.guideme.bazaar.service.CommentService;
import uz.guideme.bazaar.service.client.UserClientService;
import uz.guideme.bazaar.service.dto.CommentDTO;
import uz.guideme.bazaar.service.impl.utils.TokenUtils;
import uz.guideme.bazaar.service.mapper.CommentMapper;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserClientService userClientService;

    @Override
    public Optional<CommentDTO> create(CommentDTO commentDTO, String token, ProductEntity product) {
        log.info("Requested to create comment, {}", commentDTO);
        String username = TokenUtils.getUsername(token);
        Map<String, String> userData = userClientService.fetchUserDataByUsername(username);

        CommentEntity entity = new CommentEntity();

        entity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        entity.setRatings(commentDTO.getRatings());
        entity.setMessage(commentDTO.getMessage());
        entity.setProduct(product);
        entity.setUsername(userData.get("username"));

        entity = repository.save(entity);
        log.info("Successfully created");
        return Optional.of(CommentMapper.toDto(entity));
    }

    @Override
    public Page<CommentDTO> findAll(int page, int size, ProductEntity product) {
        log.info("Requested to get all comments");
        Page<CommentEntity> comments =repository.getAll(page,size, product.getId());
        return comments.map(CommentMapper::toDto);
    }
}
