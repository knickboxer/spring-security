package de.gdevelop.spring.security.chapter03.controller;

import de.gdevelop.spring.security.chapter03.model.User;
import de.gdevelop.spring.security.chapter03.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@RestController
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserDetails addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}
