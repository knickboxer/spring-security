package de.gdevelop.spring.security.chapter05.security.filter;

import de.gdevelop.spring.security.chapter05.security.authentication.CustomAuthentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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
public class CustomAuthenticationFilter implements Filter {

    AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest)request;
        var res = (HttpServletResponse)response;
        var authorization = req.getHeader("Authorization");
        var authentication = new CustomAuthentication(authorization, null);

        try {
            var result = authenticationManager.authenticate(authentication);
            if (result.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(result);
            chain.doFilter(request, response);
            } else {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (AuthenticationException ae) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }

    }
}
