package de.gdevelop.spring.security.chapter08.security.provider;

import de.gdevelop.spring.security.chapter08.security.authentication.TokenAuthentication;
import de.gdevelop.spring.security.chapter08.security.store.CustomTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomTokenAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    CustomTokenStore tokenStore;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var token = authentication.getName();
        var username = tokenStore.getUsernameForToken(token);
        if (username != null) {
            return new TokenAuthentication(username, token, List.of(()-> "read"));
        }
        throw new BadCredentialsException("Invalid Token");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.equals(authentication);
    }
}
