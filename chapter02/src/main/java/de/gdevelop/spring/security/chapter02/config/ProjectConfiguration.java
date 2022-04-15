package de.gdevelop.spring.security.chapter02.config;

import de.gdevelop.spring.security.chapter02.repository.UserDetailsRepository;
import de.gdevelop.spring.security.chapter02.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Gerhard
 */
@Configuration
public class ProjectConfiguration {

    final UserDetailsRepository repository;

    public ProjectConfiguration(UserDetailsRepository repository) {
        this.repository = repository;
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new JPAUserDetailsService(repository);
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
}
