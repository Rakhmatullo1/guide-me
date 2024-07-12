package uz.guideme.hotelbooking.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService<R,P> {

    Optional<R> save(MultipartFile file, P entity);
}
