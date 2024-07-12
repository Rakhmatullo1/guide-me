package uz.guideme.hotelbooking.service.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uz.guideme.hotelbooking.service.dto.UserDTO;
import uz.guideme.hotelbooking.service.exception.CustomException;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserClientService {

    private final RestTemplate restTemplate;
    @Value("${user-service.serverUrl}")
    private String url;

    public Map<String, String> fetchUserDataByUsername(String username) {
        log.info("REST request to user service ");
        HttpEntity<String> request = new HttpEntity<>(username);

        try {
            ResponseEntity<UserDTO> response = restTemplate.exchange(url+"/"+username, HttpMethod.GET, request, UserDTO.class);

            if(response.getStatusCode().is4xxClientError()) {
                log.warn("Error caught from response");
                throw new CustomException("Error caught from response");
            }

            if(Objects.isNull(response.getBody())){
                log.warn("Body is null");
                throw new CustomException("Body is null");
            }

            return Map.of(
                    "username", response.getBody().getUsername(),
                    "email", response.getBody().getEmail());

        } catch (RestClientException exception) {
            log.warn("Exception: {}", exception.getLocalizedMessage());
            throw new CustomException(exception.getLocalizedMessage());
        }
    }


}
