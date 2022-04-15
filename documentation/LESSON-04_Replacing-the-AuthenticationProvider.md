# Spring Security
[TOC](./README.md)

## Lesson 4 - Replacing the AuthenticationProvider

⭐ Timestamps ⭐
- 1:00 Recap of Spring-security Architecture
- 7:00 Creating Spring boot project with Dependencies
- 16:00 Implementing Custom AuthenticationProvider, understanding Authentication interface contracts, Using Basic Authentication
- 31:30 How AuthenticationManager finds proper AuthenticationProvider
- 44:00 Config Class with AuthenticationProvider


In this lesson a custom AuthenticationProvider is implemented with its two methods.

### Understanding the AuthenticationProvider interface

#### the authenticate(..) method

This method should implement a simple autentication logic:
If the request is authenticated then return a fully authenticated Authentication instance.
If the request is *not* authenticated then thow an AuthenticationException.
If the Authentication is not supported by this AuthenticationProvider then return **null**.


**fully authenticated* means that the Authentication instance returns true for  isAuthenticated()- method
from it's interface. Fully authenticated Auth instances will be instanciated by the AuthenticationProvider.

#### the supports(...)

This method gets the type of Authentication as parameter and checks if the AuthenticationProvider supports
the type of the current Authentication instance.

```java
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
```

The AuthenticationManager only calls the authenticate(...) method of the AuthenticationProvider
if the supports(...)- method returns true.

### The Authentication interface

The Authentication instance contains all the details for the authentication including the credentials.
The credentials should be removed after a user/subject has been authenticated

Object getCredentials() -> The password. This may be a cryptographic key or something completely different
Object getDetails()     -> the details contain special information. E.g. this is used in the OAuht 2.0 authentication.
Object getPrinciple()   -> return the user/subject for this request and can return a `java.security.Pripncipal` instance
getAuthorities()        -> returns a collection of privileges after sucessful authentication


HTTP Basic authentication uses UsernamePasswordAuthenticationToken.class


[Code for this lesson](https://github.com/lspil/youtubechannel/tree/master/ss-c4)

[TOC](./README.md)
