package de.gdevelop.spring.security.chapter07.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@RestController
public class TestController {

    @GetMapping
    public String hello() {
        return "Hello from chapter 07";
    }

}
