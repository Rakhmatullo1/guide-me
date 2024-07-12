package uz.guideme.authservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.util.HttpResponseException;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.AuthorizationResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import uz.guideme.authservice.properties.KeycloakProperties;
import uz.guideme.authservice.service.AuthService;
import uz.guideme.authservice.service.dto.request.LoginRequestDTO;
import uz.guideme.authservice.service.dto.request.RegisterRequestDTO;
import uz.guideme.authservice.service.dto.response.ResponseDTO;
import uz.guideme.authservice.service.exception.BadAuthorizeException;
import uz.guideme.authservice.service.exception.BadPasswordException;
import uz.guideme.authservice.service.exception.CustomException;
import uz.guideme.authservice.service.mapper.UserMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final Keycloak keycloak;
    private final AuthzClient authzClient;
    private final KeycloakProperties properties;

    private final RestTemplate restTemplate;

    @Override
    public Optional<ResponseDTO> login(LoginRequestDTO requestDTO) throws InterruptedException {
        log.info("Requested to login: {}", requestDTO);
        ResponseDTO responseDTO = authorizeAndGetResponse(requestDTO.getEmail(), requestDTO.getPassword());

        return Optional.of(responseDTO);
    }

    @Override
    public Optional<ResponseDTO> register(RegisterRequestDTO requestDTO) throws InterruptedException {
        log.info("Requested to register: {}", requestDTO);

        checkUserExist(requestDTO.getEmail());

        Response response;
        try {
            response = keycloak.realm(properties.getRealm()).users()
                    .create(UserMapper.toUserRepresentation(requestDTO));
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }

        if (response.getStatus() >= 400) {
            log.warn("Response: {}", response);
            throw new BadPasswordException("Illegal argument passed");
        }

        ResponseDTO responseDTO = authorizeAndGetResponse(requestDTO.getEmail(), requestDTO.getPassword());

        return Optional.of(responseDTO);
    }

    @Override
    public Optional<ResponseDTO> refresh(String refreshToken) {
        AccessTokenResponse methodResponse = null;
        AccessTokenResponse[] response = new AccessTokenResponse[0];
        try {
            methodResponse = callRefresh(refreshToken);
            response = new AccessTokenResponse[]{new AccessTokenResponse()};
            response[0] = methodResponse;
        } catch (JsonProcessingException e) {
            log.error(e.getLocalizedMessage());
            throw new CustomException(e.getLocalizedMessage());
        } catch (RuntimeException e) {
            waitCallRefresh(e, methodResponse, response);
        }
        if (isNull(response[0].getToken())) {
            if (response[0].getErrorDescription().toLowerCase().contains("invalid token issuer")) {
                log.error("Invalid token : {}", response[0].getErrorDescription());
                throw new CustomException("Invalid token");
            }
            throw new CustomException(response[0].getErrorDescription());
        }
        return Optional.of(new ResponseDTO(response[0].getToken(), response[0].getRefreshToken()));
    }

    private AccessTokenResponse callRefresh(String refresh) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED.toString());
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String url = authzClient.getServerConfiguration().getTokenEndpoint();
        String clientId = authzClient.getConfiguration().getResource();
        String secret = (String) authzClient.getConfiguration().getCredentials().get("secret");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("refresh_token", refresh);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", secret);
        requestBody.add("grant_type", "refresh_token");

        HttpEntity<MultiValueMap<String, String>> formEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<AccessTokenResponse> response =
                    restTemplate.exchange(url, HttpMethod.POST, formEntity, AccessTokenResponse.class);
            return response.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException exception) {
            log.info("Response: {}", exception.getResponseBodyAsString());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(exception.getResponseBodyAsString(), AccessTokenResponse.class);
        }
    }

    private void waitCallRefresh(RuntimeException e, AccessTokenResponse methodResponse, AccessTokenResponse[] response) {
        if (e.getMessage().contains("Failed to obtain")) {
            log.error(e.getLocalizedMessage());
            throw new BadAuthorizeException("Something wrong with token, unauthorized");
        }
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        ExecutorService threadPool = Executors.newWorkStealingPool();
        if (rootCause instanceof IOException) {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(() -> response[0] = methodResponse);
                if (response[0].getToken() != null) {
                    threadPool.shutdown();
                    break;
                }
                sleep(800);
            }
        }
    }

    private ResponseDTO authorizeAndGetResponse(String phoneNum, String password) throws BadAuthorizeException {
        final AuthorizationResponse[] response = {new AuthorizationResponse()};
        try {
            response[0] = authzClient.authorization(phoneNum, password).authorize();
        } catch (RuntimeException e) {
            if (e instanceof HttpResponseException httpResponseException && httpResponseException.getStatusCode() == 401) {
                log.error("{} :bad password, unauthorized", phoneNum);
                throw new BadPasswordException("Bad password, unauthorized");
            }
            if (e.getMessage().contains("Failed to obtain")) {
                log.error(e.getLocalizedMessage());
                throw new BadAuthorizeException("Something wrong, unauthorized");
            }
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            if (rootCause instanceof IOException) {
                ExecutorService threadPool = Executors.newWorkStealingPool();
                for (int i = 0; i < 10; i++) {
                    threadPool.execute(() -> response[0] = authzClient.authorization(phoneNum, password).authorize());
                    if (response[0].getToken() != null) {
                        threadPool.shutdown();
                        break;
                    }
                    sleep(200);
                }
            }
        }

        if (response[0].getToken() == null)
            throw new BadAuthorizeException("Could not authorized due to technical issues");

        log.info("FINISH login for user {} successfully ", phoneNum);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setAccessToken(response[0].getToken());
        responseDTO.setRefreshToken(response[0].getRefreshToken());

        return responseDTO;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void checkUserExist(String email) {
        UsersResource usersResource = keycloak.realm(properties.getRealm()).users();
        List<UserRepresentation> users = usersResource.searchByEmail(email, true);

        if(!users.isEmpty()) {
            log.warn("User exists with email {}", email);
            throw new CustomException("User exists with email");
        }
    }
}
