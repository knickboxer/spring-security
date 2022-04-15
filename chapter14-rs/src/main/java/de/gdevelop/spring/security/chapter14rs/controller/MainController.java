package de.gdevelop.spring.security.chapter14rs.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 */
@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        System.out.println(authentication);
        return "Hello!";
    }

}
