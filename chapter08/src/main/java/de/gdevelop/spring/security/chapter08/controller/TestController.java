package de.gdevelop.spring.security.chapter08.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@RestController
public class TestController {

    @GetMapping
    public String hello(Authentication authentication) {
        System.out.println(authentication);
        return "Hello from chapter 08: Authenticated user -> " + authentication.getName() ;
    }


    @GetMapping("/sch")
    public String helloAgain() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "Hello again from chapter 08: Authenticated user -> " + authentication.getName() ;
    }

    @GetMapping("/async")
    @Async
    public String helloAsync() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        return "Hello from chapter 08: Authenticated user -> " + authentication.getName() ;
    }

    // Before using this methode you have to enable the InitializingBean in ProjectConfiguration
    // or set the spring.security.stratety system property
    @GetMapping("/thread")
    public String helloThreaded() {
        Runnable runnable = () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(runnable);
        executorService.shutdown();

        return "Hello from chapter 08: With own thread" ;
    }

    @GetMapping("/delegatingRunnable")
    public String helloThreadedWithDelegationRunnable() {
        Runnable runnable = () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
        };

        DelegatingSecurityContextRunnable delegatingRunnable = new DelegatingSecurityContextRunnable(runnable);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(delegatingRunnable);
        executorService.shutdown();

        return "Hello from chapter 08: With delegated runnable" ;
    }

    @GetMapping("/delegatingExecutor")
    public String helloThreadedWithDelegatingExecutorService() {
        Runnable runnable = () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(authentication);
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService delegatingExecutorService = new DelegatingSecurityContextExecutorService(executorService);
        delegatingExecutorService.submit(runnable);
        delegatingExecutorService.shutdown();

        return "Hello from chapter 08: With executor service" ;
    }




}
