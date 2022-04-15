package de.gdevelop.spring.security.chapter10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @PostMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println(":(");
        return "TEST";
    }

    @PostMapping("/test-with-cors")
    @ResponseBody
    @CrossOrigin("*")  // DO NOT DO THIS. Allowed Origins is way to broad!
    public String testWithCORS() {
        System.out.println(":(");
        return "TEST WITH CORS";
    }



}
