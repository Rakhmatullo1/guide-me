package uz.guideme.authservice.controller.utils;

import org.springframework.http.ResponseEntity;
import uz.guideme.authservice.service.exception.CustomException;

import java.util.Optional;

public class ResponseUtils {

    public static <T> ResponseEntity<T> wrap(Optional<T> optional) {
        if(optional.isEmpty()) {
            throw new CustomException("Illegal argument Passed");
        }

        return optional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
