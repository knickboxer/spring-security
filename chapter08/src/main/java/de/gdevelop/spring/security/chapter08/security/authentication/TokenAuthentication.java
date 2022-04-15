package de.gdevelop.spring.security.chapter08.security.authentication;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public class TokenAuthentication extends UsernamePasswordAuthentication{
    public TokenAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public TokenAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
