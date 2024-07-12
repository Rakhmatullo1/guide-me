package uz.guideme.authservice.configuration;

import lombok.RequiredArgsConstructor;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.context.annotation.Bean;
import uz.guideme.authservice.properties.KeycloakProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@org.springframework.context.annotation.Configuration
@RequiredArgsConstructor
public class KeycloakConfiguration {

    private final KeycloakProperties keycloakProperties;

    @Bean
    public AuthzClient configureAuthzClient() {
        Map<String, Object> cred = new HashMap<>();
        cred.put("secret", keycloakProperties.getClientSecret());
        Configuration configuration =
                new Configuration(
                        keycloakProperties.getServerUrl(),
                        keycloakProperties.getRealm(),
                        keycloakProperties.getClientId(),
                        cred,
                        httpClient());
        return AuthzClient.create(configuration);
    }

    @Bean
    public Keycloak configureKeycloak() {
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakProperties.getServerUrl())
                .scope(keycloakProperties.getScope())
                .grantType(OAuth2Constants.PASSWORD)
                .username(keycloakProperties.getUsername())
                .password(keycloakProperties.getPassword())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .realm(keycloakProperties.getRealm())
                .build();
    }

    private CloseableHttpClient httpClient() {

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager =
                new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setValidateAfterInactivity(10);
        poolingHttpClientConnectionManager.setMaxTotal(200);

        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);

        HttpClientBuilder httpClientBuilder = HttpClients.custom();

        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        httpClientBuilder.setConnectionManagerShared(true);
        httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(4, true));
        httpClientBuilder.setConnectionTimeToLive(1, TimeUnit.HOURS);

        SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        socketConfigBuilder
                .setSoKeepAlive(true)
                .setTcpNoDelay(true)
                .setSoReuseAddress(true)
                .setRcvBufSize(6098)
                .setSndBufSize(6098)
                .setBacklogSize(50)
                .setSoLinger(5);
        SocketConfig socketConfig = socketConfigBuilder.build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        poolingHttpClientConnectionManager.setDefaultSocketConfig(socketConfig);
        return httpClientBuilder.build();
    }
}
