package uz.guideme.hotelbooking.service.client;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageClientService {

    Optional<String> uploadFile(MultipartFile file);

}
