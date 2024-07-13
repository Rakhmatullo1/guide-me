package uz.guideme.bazaar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */


public interface ImageService<R,P> {

    Optional<R> save(MultipartFile file, P entity);

    Page<R> findByEntity(P entity, Pageable pageable);
}