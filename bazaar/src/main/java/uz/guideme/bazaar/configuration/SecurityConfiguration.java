package uz.guideme.bazaar.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import uz.guideme.bazaar.security.JwtGrantedAuthorityConverter;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String SELLER = "ROLE_SELLER";

    private static final String PREFERRED_USERNAME = "preferred_username";

    private static final RequestMatcher SECURED_ENDPOINTS = new AndRequestMatcher(
            new AntPathRequestMatcher("/api/market", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/market/{id}/images", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/market/product/{id}/image", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/market/{id}/product", HttpMethod.POST.name())
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r ->
                        r.requestMatchers(SECURED_ENDPOINTS).hasAuthority(SELLER)
                                .anyRequest().permitAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter())))
                .build();
    }

    Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtGrantedAuthorityConverter());
        jwtAuthenticationConverter.setPrincipalClaimName(PREFERRED_USERNAME);
        return jwtAuthenticationConverter;
    }
}
