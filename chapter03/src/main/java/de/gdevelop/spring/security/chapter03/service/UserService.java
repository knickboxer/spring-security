package de.gdevelop.spring.security.chapter03.service;

import de.gdevelop.spring.security.chapter03.model.SecurityUser;
import de.gdevelop.spring.security.chapter03.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Service
public class UserService {

    UserDetailsManager userDetailsManager;
    PasswordEncoder passwordEncoder;

    public UserService(UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails addUser(User user) {
        var securityUser = new SecurityUser(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        userDetailsManager.createUser(securityUser);
        return userDetailsManager.loadUserByUsername(user.getUsername());
    }

}
