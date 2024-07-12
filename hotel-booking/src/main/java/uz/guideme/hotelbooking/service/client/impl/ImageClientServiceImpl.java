package uz.guideme.hotelbooking.service.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import uz.guideme.hotelbooking.service.client.ImageClientService;
import uz.guideme.hotelbooking.service.exception.CustomException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageClientServiceImpl implements ImageClientService {

    @Value("${file-service.serverUrl}")
    private String serverUrl;

    private final RestTemplate restTemplate;

    @Override
    public Optional<String> uploadFile(MultipartFile file) {
        log.info("REST request to upload for file-service");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("file", file.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, headers);

        try {
            HttpEntity<String> response = restTemplate.exchange(serverUrl, HttpMethod.POST, requestEntity, String.class);
            log.info("Response: {}", response);
            return Optional.ofNullable(response.getBody());
        } catch (RestClientException ex) {
            log.warn(ex.getLocalizedMessage());
            throw new CustomException(ex.getMessage());
        }
    }
}
