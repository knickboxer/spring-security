package de.gdevelop.spring.security.chapter12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

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

    @Autowired
    private UserDetailsService userDetailsService;

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
                    // PASSWORD GRANT TYPE
                    .withClient("client1")
                    .secret("secret1")
                    .scopes("read")
                    .authorizedGrantTypes("password")
                    .autoApprove("read")
                .and()
                    // AUTHORIZATION CODE GRANT TYPE
                    .withClient("client2")
                    .secret("secret2")
                    .scopes("read")
                    .authorizedGrantTypes("authorization_code")
                    .redirectUris(
                            "http://localhost:8080/auth-code",
                            "http://127.0.0.1:8080/auth-code")
                    .autoApprove("read")
                .and()
                    // CLIENT CREDENTIALS GRANT TYPE
                    .withClient("client3")
                    .secret("secret3")
                    .scopes("read")
                    .authorizedGrantTypes("client_credentials")
                    .autoApprove("read")
                .and()
                    // REFRESH TOKEN GRANT TYPE
                    .withClient("client4")
                    .secret("secret4")
                    .scopes("read")
                    .authorizedGrantTypes("password","refresh_token")
                    .autoApprove("read")
        ;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        // To change from opaque token to JWT you have to set at least an accessTokenConverter.
        // The adapter then will automatically choose the correct token store
                //.accessTokenConverter(converter());

    }

    //    @Bean
    //    public TokenStore tokenStore() {
    //        return new JwtTokenStore(converter());
    //    }

    @Bean
    public JwtAccessTokenConverter converter(){
        return new JwtAccessTokenConverter();
    }
}
