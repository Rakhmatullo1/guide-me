package uz.guideme.hotelbooking.configuration;

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
import uz.guideme.hotelbooking.security.JwtGrantedAuthorityConverter;

@Configuration
public class SecurityConfiguration {

    private static final String HOTEL_OWNER = "ROLE_HOTEL_OWNER";

    private static final String PREFERRED_USERNAME = "preferred_username";

    private static final RequestMatcher SECURED_ENDPOINTS = new AndRequestMatcher(
            new AntPathRequestMatcher("/api/hotels", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/hotels/{id}", HttpMethod.PUT.name()),
            new AntPathRequestMatcher("/api/hotels/{id}", HttpMethod.DELETE.name()),
            new AntPathRequestMatcher("/api/hotels/images", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/hotels/rooms", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/hotels/rooms/add-facilities", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/hotels/rooms/images", HttpMethod.POST.name()),
            new AntPathRequestMatcher("/api/hotels/bookings/{id}/make-decision", HttpMethod.POST.name()),
            new AntPathRequestMatcher("api/hotels/email")
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r ->
                        r.requestMatchers(SECURED_ENDPOINTS).hasAnyAuthority(HOTEL_OWNER)
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
