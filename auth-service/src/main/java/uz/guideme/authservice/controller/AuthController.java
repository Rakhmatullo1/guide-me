package uz.guideme.authservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import uz.guideme.authservice.controller.utils.ResponseUtils;
import uz.guideme.authservice.service.AuthService;
import uz.guideme.authservice.service.dto.request.LoginRequestDTO;
import uz.guideme.authservice.service.dto.request.RefreshTokenRequestDTO;
import uz.guideme.authservice.service.dto.request.RegisterRequestDTO;
import uz.guideme.authservice.service.dto.response.ResponseDTO;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final static String RESPONSE = "Response: {}";

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) throws InterruptedException {
        log.debug("REST request to login");
        Optional<ResponseDTO> result = authService.login(requestDTO);

        ResponseEntity<ResponseDTO> response = ResponseUtils.wrap(result);
        log.debug(RESPONSE, response);
        return response;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO requestDTO) throws InterruptedException {
        log.debug("REST request to register");
        Optional<ResponseDTO> result = authService.register(requestDTO);

        ResponseEntity<ResponseDTO> response = ResponseUtils.wrap(result);
        log.debug(RESPONSE, response);
        return response;
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<ResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO requestDTO) {
        log.debug("REST request to refresh token");
        Optional<ResponseDTO> result = authService.refresh(requestDTO.getRefreshToken());

        ResponseEntity<ResponseDTO> response = ResponseUtils.wrap(result);
        log.debug(RESPONSE, response);
        return response;
    }
}
