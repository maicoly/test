package ec.fin.baustro.servicevu.provider;

import ec.fin.baustro.servicevu.model.ApiKeyAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
@Slf4j
@Component
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {
    @Value("${ba.apikey}")
    private String apikeyBA;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String apiKey = (String) authentication.getPrincipal();

        if (ObjectUtils.isEmpty(apiKey)) {
            log.error("No se ha encontrado el API key en la solicitud");
            throw new InsufficientAuthenticationException("No API key in request");
        } else {
            if (apikeyBA.equals(apiKey)) {
                log.info("El API key encontrada es valida");
                return new ApiKeyAuthenticationToken(apiKey, true);
            }
            log.error("El API key encontrada no es valida");
            throw new BadCredentialsException("API Key is invalid");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}