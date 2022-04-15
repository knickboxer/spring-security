package de.gdevelop.spring.security.chapter01.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author Gerhard
 */
@Configuration
public class ProjectConfiguration {


    @Bean
    public UserDetailsService userDetailsService() {
        var userDetailsService =  new InMemoryUserDetailsManager();
        var user = User.withUsername("bill")
                .password("12345")
                // .password("{noop}12345")  // mind the password encodingId at the password
                .authorities("read")
                .build();
        userDetailsService.createUser(user);
        return userDetailsService;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        /* This implementation is equal to the NoOpPasswordEncoder */
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {  return rawPassword.toString(); }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

}
