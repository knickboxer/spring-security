package de.gdevelop.spring.security.chapter07.security.filter;

import de.gdevelop.spring.security.chapter07.security.authentication.TokenAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gerhard
 */
@Component
public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    public CustomTokenAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = request.getHeader("Authorization");
        var authentication = new TokenAuthentication(token, null);

        var authenticated = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authenticated);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/login");
    }
}
