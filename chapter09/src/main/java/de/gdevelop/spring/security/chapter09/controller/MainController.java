package de.gdevelop.spring.security.chapter09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Controller
public class MainController {

    @GetMapping
    public String main() {
        return "main.html";
    }

    @PostMapping("/change") // POST, PUT, DELETE mutating action in REST
    public String doSomething() {
        System.out.println(":(");
        return "main.html";
    }

}
