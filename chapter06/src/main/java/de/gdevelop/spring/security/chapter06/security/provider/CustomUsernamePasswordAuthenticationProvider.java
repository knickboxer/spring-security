package de.gdevelop.spring.security.chapter06.security.provider;

import de.gdevelop.spring.security.chapter06.security.authentication.UsernamePasswordAuthentication;
import de.gdevelop.spring.security.chapter06.service.JpaUserDetailsService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Gerhard
 */
@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private JpaUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public CustomUsernamePasswordAuthenticationProvider(JpaUserDetailsService userDetailsService, @Lazy PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        var userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthentication(username, password, List.of(() -> "read"));
        }
        throw  new BadCredentialsException("User not authenticated");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
