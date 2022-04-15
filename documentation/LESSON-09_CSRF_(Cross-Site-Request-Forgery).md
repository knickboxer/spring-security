# Spring Security
[TOC](./README.md)

## Lesson 9 - CSRF (Cross-Site Request Forgery)

⭐ Timestamps ⭐
- 2:00 CSRF example
- 16:00
    1. CSRF Attack for CRUD operation by disabling csrf ,
    2. CSRF Token sent by Server on GET and validated for subsequent CUD operations 3.CSRF valid for Session Context
- 32:00 Adding a CSRF token to the web page
- 39:00 QA on how CSRF Attack Possible even with CSRF protection
- 42:00 Customize CSRF protection with a Customizer
- 48:00 Custom CSRF token Management using CSRF Repository
- 56:00 Spring Security CSRFFilter handles CSRF protection

Starting by a simple WebApplication with a login form, you will find an hidden html input element "_csrf"
The value assigned to this element is the CSRF token.


```html
  ...
  <input name="_csrf" type="hidden" value="b51cb2ad-eabf-4f80-8c6c-9efb4da8dc9d">
  ...
```

It's very easy to disable the csrf token from a configuration class and overriding the configure method of the `WebSecurityConfigurerAdapter`.
After applying that configuration the html input element will be removed from the page and no CSRF Token will be checked any
more. The CSRF-Filter (`CsrfFilter`) will be removed from the filterchain.

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable(); // !!! GENERALLY DO NOT DO THIS !!!
    }
```

After that it's easy to call a POST/PUT/DELETE Action on a spring Controller class and you can easily be tricked into
executing action you don't want to by an malicious attacker. (see `malicious.html`).

That's the reason why in spring security the CSRF protection is enabled by default.

So **never ever disable CSRF!**. At least until some other countermeasures are in place. (-> e.g. an OAuth 2 Token filter).

### How it works

Spring Security generates a random token and sends it with the reponse (html page).
When the user sends a request back to the server this token has to be part of the request data.
If the token is not available, the server refuses to process the request of the user.

So you have to add the CSRF token to the html pages where an action is taken.

```html
  <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
```

With this, it's not possible any more to call a request without the CSRF token.
The malicious page won't work any more.


### Configuring CSRF protection by a CsrfConfigurer

One thing you can do is to disable csrf for some pathes define by Ant Pattern.

```java
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf(configurer -> {
            configurer.ignoringAntMatcher("AntPattern 1", "AntPattern 2", ...); //
        });
    }
```

It's also possible to customize the Tokens management by defining your own CsrfTokenRepository.
`CsrfTokenRepository` is an interface that defines three methods:
- generateToken(HttpServletRequest req)
- saveToken(CsrfToken token, HttpServletRequest req, HttpServletResponse response)
- loadToken(HttpServletRequest req);

```java
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf(configurer -> {
            configurer.csrfTokenRepository( myTokenRepository() );
        });
    }
```

And by this you can create custom CsrfTokens. `CsrfTokens` is another interface that defines a header name, a parameter
name and the token itself as a string. But it's best to use `DefaultCsrfToken` implemenation class, that's implemented
as a ValueObject and includes a header name, a parameter name (*_csrf* by default) and the
token itself

By implementing a custom CsrfTokenRepository you can set the token to a fixed value, what's normally a bad idea. See
`CustomCsrfTokenRepository` example.


### When not to use CSRF Protection

CSRF is not need for OAuth 2 Authorization.

### Add a Filter for CsrfToken to log the tokens

You can easily to log the CsrfToken after it has been generated in the CsrfFilter
by adding another filter after the `CsrfFilter`. See the `CustomCsrfTokenLoggerFilter` example.

See also: [Was ist CSRF (Cross-Site-Request-Forgery-Attack)?](https://www.ionos.de/digitalguide/server/sicherheit/was-ist-cross-site-request-forgery/)

[TOC](./README.md)
