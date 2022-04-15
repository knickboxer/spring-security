# Spring Security
[TOC](./README.md)

## Lesson 6 - Multiple Authentication Providers Part 1

⭐ Timestamps ⭐
- 0:30 Implementing Multi Factor Authentication(Basic & OTP Authentication)
- 26:50 Configuring Authentication Manager explicitly
- 50:00 Configuring Authentication Provides and Filters
- 58:00 Returning a token after Authentication


In this level a multi-level authentication (not MFA, because there are no differnt factors).
In the first step the user authenticates with username/password, then in the second step the user has to enter a
one-time password (a token created from a UUID) for authentication.


1. Configure a database
2. Create User and an Otp Entities with according Repositories
3. Create a UserDetailsService and define a PasswordEncoder-Bean
4. Create a filter for User Authentication extending `OncePerRequestFilter`. In this Filter override , the
   `doFilterInternal(...)`- method  will only be called, when the ServletPath starts with __/login__. Therefore you
   have to override the `shouldNotFilter()`-method.
5. Create a AuthenticationProvider `CustomUsernamePasswordAuthFilter` for first part: Username and password authentication
6. if authentication is valid, create an Otp for the user and save it to the database in `CustomUsernamePasswordAuthFilter`.
7. Create a AuthenticationProvider `CustomOtpAuthFilter` for second part: Username and password authentication;
8. Add Filter and Provider in WebSecurityConfiguration by overriding two `configure(...)` methods.

How to elaborate this lesson

- In addition to the lesson a timestamp is added to the OTP so it will expire after 30 seconds.
- OTP handling is done in the AuthenticationFilter, this should be extracted to an OTP Service or OTP Provider that generates
  a second factor.

[TOC](./README.md)
