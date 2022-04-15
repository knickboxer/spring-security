# Spring Security
[TOC](./README.md)

## Lesson 8 - The Security Context

⭐ Timestamps ⭐
- 17:30 Retrieving Authentication from Security Context in Resource API End point
- 24:00 Access Authentication via SecurityContextHolder
- 27:00 Threadlocal implementation of the SecurityContextHolder
- 33:00 Security Context is ThreadLocal Mode by Default, How to change Mode , and other means to make context available across Threads

This lesson continues where lesson 7 stopped. The


In Lesson 8 the authentication ios injected into a Controller. Spring knows how to take the Authentication object from the
`SecurityContext` and injects it into spring managed components.
The details of the Authentication object can be accessed by the Principal contract.

??? _Can the Authentication only be injected in Controller class._ ???

Besides injection the Authentication object it is possible to access the object directly from the SecurityContext.
With `Authentication authentication = SecurityContextHolder.getContext().getAuthentication()` you can get the current
authentication object anywhere in the application.

SecurityContextHolder uses a ThreadLocal implementation for holding the Authentication object. With the standard
Spring MVC every request will be handled in its own thread. So the authentication will always be available. But if you
use Spring WebFlux and work with reactive applications or use asynchonous processing in Controller classes (**@Async**),
you don't have access to the Authentication out of the box.

(To enable Asynchronous processing you have to add the `@EnableAsync` Annotation to a configuration class).
To change this behaviour you have to change the strategy of the `SecurityContextHolder` to manage the SecurityContext.
`SecurityContextHolder` implements three different strategies to manage the SecurityContext. The default strategy
is **MODE_THREADLOCAL**, which stores a state or values only for the thread it belongs to.
Next is the **MODE_INERITABLETHREADLOCAL** propapates the properties to a new created thread that forke /spawned from the
original thread.

To use **MODE_INERITABLETHREADLOCAL** you have to set this, as is done in the configuration file with an `InitializingBean`.
An InitializingBean will be be of use to set the strategy before the first use.

```java
    @Bean
    public InitializingBean initializingBean() {
        return() -> {
            SecurityContextHolder.setStrategyName(
                    SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        };
    }
```

Another way to set the strategy is by defining the configuration setting orsystem property `spring.security.strategy`.

By using **MODE_INERITABLETHREADLOCAL** strategy it also works when you create the thread on your own, as can be seen in
the `TestController.helloThreaded()` method.

It's even possible to delegate the SecurityContext without setting the  **MODE_INERITABLETHREADLOCAL** strategy.
Therefore you can use either a DelegatingSecurityContextRunnable as in `TestController.helloThreadedWithDelegatingRunnable()`
or with a `TestController.helloThreadedWithDelegatingExecutorService()`

The preferred way is to yous @Async and @EnableAsync with some sort of configuration of the strategy.


```java
  DelegatingSecurityContextRunnable delegatingRunnable = new DelegatingSecurityContextRunnable(runnable);
 ```
```java
  ExecutorService delegatingExecutorService = new DelegatingSecurityContextExecutorService(executorService);
 ```




If you check the latest spring code for SecurityContextHolder, you can see that depending on the strategy you use, it
either initializes a `ThreadLocalSecurityContextHolderStrategy` object or a `InheritableThreadLocalSecurityContextHolderStrategy`
object which actually contains the `ThreadLocal<SecurityContext>`.
The difference is that the `ThreadLocalSecurityContextHolderStrategy` object contains a plain `java.lang.ThreadLocal` and the
`InheritableThreadLocalSecurityContextHolderStrategy` contains the `java.lang.InheritableThreadLocal` variable.

So, spring is basically using the java.lang.InheritableThreadLocal variable, which leads to the JVM itself to propogate
the thread local variable to the child thread. This is the reason why you are able to access the authentication even when
you create your own thread.

Possibly the `DelegatingSecurityContextRunnable` does the propogation on a regular ThreadLocal variable, so that you can
selectively decide whether to propogate the context to the new thread.

[TOC](./README.md)
