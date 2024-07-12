package uz.guideme.authservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("keycloak")
@Data
public class KeycloakProperties {
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private String scope;
    private String serverUrl;
    private String realm;
}
