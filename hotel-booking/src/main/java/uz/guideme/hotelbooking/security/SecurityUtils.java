package uz.guideme.hotelbooking.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import uz.guideme.hotelbooking.service.exception.NotFoundException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class SecurityUtils {
    private static final String USER_ID_CLAIM_NAME = "sub";
    private static final String USER_ROLE_CLAIM_NAME = "realm_access";
    private final static String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    public static UUID getCurrentUserId() {
        String userIdAsString = (String) getClaimByName()
                .orElseThrow(() -> new NotFoundException("UserID(sub claim) is not found in token!"));

        return UUID.fromString(userIdAsString);
    }

    @SuppressWarnings("unchecked")
    public static String getUserType() {
        List<String> roles = (List<String>) Optional.ofNullable(getAllClaims().get(USER_ROLE_CLAIM_NAME))
                .orElseThrow(()->new NotFoundException("User Role is not found in token"));

        return roles.stream().filter(role->role.startsWith("ROLE_")).findFirst().orElse(ROLE_ANONYMOUS);
    }

    public static Map<String, Object> getAllClaims() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (Objects.isNull(auth)) {
            log.warn("Authentication is not found");
        }

        return ((JwtAuthenticationToken) auth).getToken().getClaims();
    }

    private static Optional<Object> getClaimByName() {
        return Optional.ofNullable(getAllClaims().get(USER_ID_CLAIM_NAME));
    }

    public static List<GrantedAuthority> extractAuthoritiesByClaim(Map<String, Object> claims) {
        return mapRoles2Authorities(getRoles(claims));
    }

    @SuppressWarnings("unchecked")
    private static Collection<String> getRoles(Map<String, Object> claims) {
        Map<String, Object> realmAccess = ((Map<String, Object>) claims.get("realm_access"));
        return ((Collection<String>) realmAccess.get("roles"));
    }


    private static List<GrantedAuthority> mapRoles2Authorities(Collection<String> roles) {
        return roles.stream().filter(role -> role.startsWith("ROLE_")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
