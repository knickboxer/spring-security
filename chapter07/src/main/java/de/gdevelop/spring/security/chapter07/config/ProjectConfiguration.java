package de.gdevelop.spring.security.chapter07.config;

import de.gdevelop.spring.security.chapter07.security.filter.CustomTokenAuthenticationFilter;
import de.gdevelop.spring.security.chapter07.security.filter.CustomUsernamePasswordAuthFilter;
import de.gdevelop.spring.security.chapter07.security.provider.CustomOtpAuthenticationProvider;
import de.gdevelop.spring.security.chapter07.security.provider.CustomTokenAuthenticationProvider;
import de.gdevelop.spring.security.chapter07.security.provider.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
public class ProjectConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    private CustomOtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private CustomTokenAuthenticationProvider tokenAuthenticationProvider;

    @Autowired
    private CustomUsernamePasswordAuthFilter usernamePasswordAuthFilter;
    @Autowired
    private CustomTokenAuthenticationFilter tokenAuthenticationFilter;



    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    @Lazy
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider )
            .authenticationProvider(otpAuthenticationProvider)
            .authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(usernamePasswordAuthFilter, BasicAuthenticationFilter.class)
                .addFilterAt(tokenAuthenticationFilter, BasicAuthenticationFilter.class);

    }
}
