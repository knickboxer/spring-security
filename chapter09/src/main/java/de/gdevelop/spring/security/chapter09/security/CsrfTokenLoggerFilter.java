package de.gdevelop.spring.security.chapter09.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CsrfTokenLoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.printf("CSRF-Token: %s%n", token.getToken() );

        filterChain.doFilter(request, response);
    }
}
