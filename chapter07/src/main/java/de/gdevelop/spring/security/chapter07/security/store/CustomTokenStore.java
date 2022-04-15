package de.gdevelop.spring.security.chapter07.security.store;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gerhard
 */
@Component
public class CustomTokenStore {

    Map<String, String> tokenMap;

    public CustomTokenStore() {
        this.tokenMap = new HashMap<>();
    }

    public void addToken(String token, String username) {
        this.tokenMap.put(token, username);
    }

    public String getUsernameForToken(String token) {
        return tokenMap.get(token);
    }
}
