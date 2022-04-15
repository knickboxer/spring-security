# Spring Security
[TOC](./README.md)

## Lesson 7 - Multiple Authentication Providers Part 2

⭐ Timestamps ⭐
- 1:00 Recap of Spring-security Architecture
- 5:00 Implementing Authentication filter for resource APIs' using token
- 45:00 Adding Authentication to Security Context as End Step after Resource API is Authenticated

Lesson 7 continues to evolve the codebase from lesson 6 by adding an CustomTokenFilter and CustomTokenAuthenticationProvider.
The filter accesses the Authorization header from the HttpServletRequest and looks up, if a token exists.
If the token exists, the username AuthenticationProvider gets the username by the token and creates a new AuthenticationObject.
This Authentication is stored in the security context.

How to elaborate this lesson

- Create a contract for the TokenStore and provide a more intelligent store.
- The token should exipre.

[TOC](./README.md)
