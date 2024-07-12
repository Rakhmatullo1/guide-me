package uz.guideme.authservice.service.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    private String refreshToken;
}
