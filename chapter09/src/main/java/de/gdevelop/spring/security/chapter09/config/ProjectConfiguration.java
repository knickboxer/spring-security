package de.gdevelop.spring.security.chapter09.config;

import de.gdevelop.spring.security.chapter09.security.CsrfTokenLoggerFilter;
import de.gdevelop.spring.security.chapter09.security.CustomCsrfTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class ProjectConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomCsrfTokenRepository csrfTokenRepository;

    @Autowired
    CsrfTokenLoggerFilter csrfTokenLoggerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        // http.csrf().disable(); // !!! GENERALLY DO NOT DO THIS !!!

        http.csrf(c -> {
                    c.ignoringAntMatchers("/csrf-disabled/**");
                    // Add a Repo that generates a fixed token value for requests.
                    // c.csrfTokenRepository(csrfTokenRepository);
                }
        );

        http.addFilterAfter(csrfTokenLoggerFilter, CsrfFilter.class);
    }

}
