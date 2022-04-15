package de.gdevelop.spring.security.chapter14rs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests( authz -> authz
                    .antMatchers("/hello").authenticated()
                    .anyRequest().authenticated()
                )
                .oauth2ResourceServer(configurer ->
                    configurer.opaqueToken(opaqueTokenConfigurer ->
                        opaqueTokenConfigurer
                            .introspectionUri("http://127.0.0.1:8080/oauth/check_token")
                            .introspectionClientCredentials("resourceserver", "12345");
                    );
                );

    }
}
