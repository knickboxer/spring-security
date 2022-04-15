package de.gdevelop.spring.security.chapter10.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
public class ProjectConfig extends WebSecurityConfigurerAdapter {

    @Value("${chapter10.allowed.origins}")
    private List<String> allowedOrigins;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // Don't do this!!! -> Here it is just to focus on CORS
        http.authorizeRequests()
                .anyRequest().permitAll();

        http.cors( c-> {
            CorsConfigurationSource ccs = request -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedMethods(List.of("GET", "POST", "PUT") );
                cc.setAllowedOrigins(allowedOrigins);
                return  cc;
            };
            c.configurationSource(ccs);
        });
    }
}
