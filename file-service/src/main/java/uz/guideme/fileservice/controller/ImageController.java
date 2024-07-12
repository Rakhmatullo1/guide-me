package uz.guideme.fileservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.fileservice.controller.utils.ResponseUtils;
import uz.guideme.fileservice.service.FileSystemService;
import uz.guideme.fileservice.service.exception.InvalidArgumentException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final FileSystemService fileSystemService;

    @PostMapping("/api/file")
    public ResponseEntity<String> store(@RequestParam("file") MultipartFile file) {
        log.debug("REST request to store file");
        Optional<Resource> resource = fileSystemService.store(file);

        if (resource.isEmpty()) {
            throw new InvalidArgumentException("Invalid argument passed");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.TEXT_PLAIN))
                .body(resource.get().getFilename());
    }

    @GetMapping("/api/file")
    public ResponseEntity<Resource> fetch(@RequestParam String filePath) {
        log.debug("REST request to fetch file");
        Optional<Resource> resource = fileSystemService.fetch(filePath);

        ResponseEntity<Resource> response = ResponseUtils.wrap(resource);
        log.debug("Response: {}", response);
        return response;
    }
}
