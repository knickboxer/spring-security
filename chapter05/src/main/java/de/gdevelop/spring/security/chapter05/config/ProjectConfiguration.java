package de.gdevelop.spring.security.chapter05.config;

import de.gdevelop.spring.security.chapter05.security.filter.CustomAuthenticationFilter;
import de.gdevelop.spring.security.chapter05.security.filter.CustomAuthenticationOncePerReqFilter;
import de.gdevelop.spring.security.chapter05.security.provider.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Configuration
public class ProjectConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationFilter filter;

    @Autowired
    CustomAuthenticationOncePerReqFilter filterOncePerRequest;



    @Autowired
    CustomAuthenticationProvider provider;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.addFilterBefore(filter, BasicAuthenticationFilter.class);
        http.addFilterBefore(filterOncePerRequest, BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }
}
