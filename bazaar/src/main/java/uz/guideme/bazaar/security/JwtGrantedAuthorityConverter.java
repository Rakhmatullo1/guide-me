package uz.guideme.bazaar.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;

/**
 * @author Rahmatullo Omonov
 * Date: 12/07/24
 */
public class JwtGrantedAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        return SecurityUtils.extractAuthoritiesByClaim(source.getClaims());
    }
}
