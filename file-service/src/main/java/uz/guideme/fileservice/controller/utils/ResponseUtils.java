package uz.guideme.fileservice.controller.utils;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import uz.guideme.fileservice.service.exception.InvalidArgumentException;

import java.util.Objects;
import java.util.Optional;

public class ResponseUtils {

    private final static String HEADER_FILE_PATH = "X-File-Path-Name";

    public static  ResponseEntity<Resource> wrap(Optional<Resource> optional) {
        if(Objects.isNull(optional) || optional.isEmpty()) {
            throw new InvalidArgumentException("Invalid argument passed");
        }

        return optional.map(value->ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG.toString(), MediaType.IMAGE_JPEG.toString())
                .header(HEADER_FILE_PATH,  value.getFilename())
                .body(value)
        ).orElse(ResponseEntity.notFound().build());
    }
}
