package de.gdevelop.spring.security.chapter02.service;

import de.gdevelop.spring.security.chapter02.entity.User;
import de.gdevelop.spring.security.chapter02.repository.UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public class JPAUserDetailsService implements UserDetailsService {

    UserDetailsRepository userDetailsRepository;

    public JPAUserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optUser = userDetailsRepository.findByUsername(username);
        User user = optUser.orElseThrow(() ->  new UsernameNotFoundException("Unknown User!"));

        return new AuthenticatedUser(user);
    }
}
