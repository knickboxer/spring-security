package de.gdevelop.spring.security.chapter03.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gerhard
 */
@RestController
public class TestController {

    @GetMapping
    public String test() {
        return "Hello from Chapter3!";
    }

}
