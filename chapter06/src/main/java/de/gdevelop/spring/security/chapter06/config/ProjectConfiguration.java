package de.gdevelop.spring.security.chapter06.config;

import de.gdevelop.spring.security.chapter06.security.filter.CustomUsernamePasswordAuthFilter;
import de.gdevelop.spring.security.chapter06.security.provider.CustomOtpAuthenticationProvider;
import de.gdevelop.spring.security.chapter06.security.provider.CustomUsernamePasswordAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Gerhard
 */
@Configuration
public class ProjectConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;
    @Autowired
    private CustomOtpAuthenticationProvider otpAuthenticationProvider;
    @Autowired
    private CustomUsernamePasswordAuthFilter usernamePasswordAuthFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthenticationProvider )
            .authenticationProvider(otpAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(usernamePasswordAuthFilter, BasicAuthenticationFilter.class);
    }
}
