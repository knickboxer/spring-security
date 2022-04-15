package de.gdevelop.spring.security.chapter03.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
public class ProjectConfiguration{

    final
    DataSource dataSource;

    public ProjectConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // To create a user with bcrypt password CSRF has to be disabled and the /user endpoint shouldn't be secured
    // You also have to extend the WebSecurityConfigurerAdapter
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic();
//
//        http.csrf().disable(); // CSRF tokens ...
//
//        http.authorizeRequests()
//                .mvcMatchers("/user").permitAll()
//                .anyRequest().authenticated();
//    }

}
