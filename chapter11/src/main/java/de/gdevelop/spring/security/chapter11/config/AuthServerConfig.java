package de.gdevelop.spring.security.chapter11.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    /*
    authorization code /  w pkce (piksi)
    password ----> deprecated
    client_credentials
    refresh_token
    implicit ----> deprecated
    */

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                    .withClient("client1")
                    .secret("secret1")
                    .scopes("read")
                    .authorizedGrantTypes("password")
                    .autoApprove("read")
                .and()
                    .withClient("client2")
                    .secret("secret2")
                    .scopes("read")
                    .authorizedGrantTypes("authorization_code")
                    .redirectUris(
                            "http://localhost:8080/auth-code",
                            "http://127.0.0.1:8080/auth-code",
                            "http://localhost:8080/auth-token",
                            "http://127.0.0.1:8080/auth-token")
                    .autoApprove("read");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
