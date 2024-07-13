package uz.guideme.bazaar.service.client;


import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * @author Rahmatullo Omonov
 * Date: 13/07/24
 */

public interface ImageClientService {

    Optional<String> uploadFile(MultipartFile file);

}
