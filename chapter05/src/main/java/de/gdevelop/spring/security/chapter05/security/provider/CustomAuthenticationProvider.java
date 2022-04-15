package de.gdevelop.spring.security.chapter05.security.provider;

import de.gdevelop.spring.security.chapter05.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
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
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var requestKey = authentication.getName();

        if (key.equals(requestKey)) {
            return new CustomAuthentication(key, null, List.of(() -> "read"));
        } else {
            throw new BadCredentialsException("Wrong key!");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
