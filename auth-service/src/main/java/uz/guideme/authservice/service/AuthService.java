package uz.guideme.authservice.service;

import uz.guideme.authservice.service.dto.request.LoginRequestDTO;
import uz.guideme.authservice.service.dto.request.RegisterRequestDTO;
import uz.guideme.authservice.service.dto.response.ResponseDTO;

import java.util.Optional;

public interface AuthService {
    Optional<ResponseDTO> login(LoginRequestDTO requestDTO) throws InterruptedException;

    Optional<ResponseDTO> register(RegisterRequestDTO requestDTO) throws InterruptedException;

    Optional<ResponseDTO> refresh(String refreshToken);
}
