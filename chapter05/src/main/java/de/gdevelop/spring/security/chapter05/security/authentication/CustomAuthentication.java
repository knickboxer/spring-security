package de.gdevelop.spring.security.chapter05.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public class CustomAuthentication extends UsernamePasswordAuthenticationToken {

    public CustomAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
