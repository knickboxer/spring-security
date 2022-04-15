package de.gdevelop.spring.security.chapter04.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // implement the authentication logic

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // if the request is authenticated you should return a fully authenticated Authentication instance
        if (userDetails != null) {
            if (passwordEncoder.matches(password, userDetails.getPassword())) {
                 return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
            }
        }

        // if the request is not authenticated you should throw AuthenticationException
        throw new BadCredentialsException("Error!");

        // if the request is not supported by this AuthenticationProvider then -> return null;
        // This case isn't supported here.

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
