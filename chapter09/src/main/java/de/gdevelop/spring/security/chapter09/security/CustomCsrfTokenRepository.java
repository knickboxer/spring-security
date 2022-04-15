package de.gdevelop.spring.security.chapter09.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomCsrfTokenRepository implements CsrfTokenRepository {

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {

        return new DefaultCsrfToken(
                "X-CSRF-TOKEN",
                "_csrf",
                "0123456789");  // It's always the same value. Normally a bad idea !!!
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return null;
    }
}
