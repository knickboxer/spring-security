package de.gdevelop.spring.security.chapter05.security.filter;

import de.gdevelop.spring.security.chapter05.security.authentication.CustomAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomAuthenticationOncePerReqFilter extends OncePerRequestFilter {

    AuthenticationManager authenticationManager;

    public CustomAuthenticationOncePerReqFilter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");
        var authentication = new CustomAuthentication(authorization, null);

        try {
            var result = authenticationManager.authenticate(authentication);
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (AuthenticationException ae) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
