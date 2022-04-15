package de.gdevelop.spring.security.chapter12.controller;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @GetMapping("/auth-code")
    public String code(@RequestParam String code) {
        System.out.println("Code: " + code);
        return "authcode:" + code;
    }

    @GetMapping("/auth-token")
    public String token(@RequestParam String token) {
        System.out.println("Token: " + token);
        return "token: " + token;
    }



}
