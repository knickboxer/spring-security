# Spring Security
[TOC](./README.md)

## Lesson 11 - OAuth 2 and Spring Security

⭐ Timestamps ⭐
- 5:00 Oauth2 concept, Custom Authorization Server implementation
- 18:00 Start implementing  Authorization Server
- 27:30 Authorization Server client configuration
- 30:00 Client ID/Secret/Scope similar to User management
- 33:00 Oauth Grant Types - How Client fetches Token from Authorization Server on behalf of User
- 37:30 Authorization Server endpoint configuration.
- 45:00 What is bearer token?
- 47:00 Token implementation : Opaque token, JWT token
- 54:00 Authorization Code Grant Type

### Notice

Creating your own authorization server is a bit difficult because the old authorization server from the spring cloud project
from spring cloud project is deprecated for some time now and the new spring authorzation server is under development and
doesn't work well. (At least there is no stable version for Spring Boot 2.6 )
There your best option is to use she authz server from spring cloud.

So you have to provide the pom management for `spring-cloud-dependencies` and use correct release for the Spring Boot version
(here Spring Cloud Release Train 2021.0.x aka Jubilee for Spring Boot version 2.6.x )

```xml 
    <dependencies>
    
        ...
            
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
            <version>2.2.0.RELEASE</version>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>2021.0.1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

### Password grant type

To get started with the, the following image shows the password grant type

![Password Grandtype](images/oauth2-granttype-password.png)

With the password grant type the username and password will be provided to the client, and the client uses the credentials
to authenticate the user with the authorization server. The Authorziation Server provides the client with a token, when
the credentials are valid to identify the user.  
The password grant type is only applicable for non-public clients, because the client knows the credentials!



### Configuration of http authentication

Implementation starts with extending WebSecurityConfigurerAdapter and provide  in Memory usermanagement for default authentication.
Hence you implement a UserDetailsServer and PasswordEncoder (as above).

### Configuration of Authorization Server for Password Grant Type

Similar to using the WebSecurityConfigurerAdapter you begin with extending the AuthorizationServerConfigurerAdapter
and override two of three `configure()` methods. One for the client details and one for the
authorization endpoints to plug in the AuthenticationManager and use the web security configuration as for a simple web application.


```java
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory(). 
            ...
            .build();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
    }
}
```
### Configuration of ClientDetails

As with UserDetailsService there as similiar possibilities to implement ClientDetailsService to create a
service that manages `ClientDetails`.

The most important Properties for a client to use password Grant type are :

```java
    clients.inMemory()
        .withClient("client1")
        .secret("secret1")
        .scopes("read")
        .authorizedGrantTypes("password") 
```


Clients are specified in an `ClientDetails` object that is best done by a `ClientBuilder` that can be retrieved from
`ClientDetailsServiceBuilder`´s  `withClient( String clientId)` method.

Here you find a list of properties/details for a clien.
```java
    private final String clientId;
    private String secret;
    private Collection<String> authorizedGrantTypes;
    private Collection<String> authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private Collection<String> scopes;
    private Collection<String> autoApproveScopes;
    private Set<String> registeredRedirectUris;
    private Set<String> resourceIds;
    private boolean autoApprove;
    private Map<String, Object> additionalInformation;
```

### Configuring the endpoint

Next step after configuring the ClientDetails is the configuration of the endpoint, therefore the authentication manager is
supplied to the endpoint for authorization.

After this final step you can start the application, that provides us with a few endpoint, that can be used by clients.
- **/oauth/token**: The endpoint to retrieve a token
- /**oauth/token_key**: The endpoint to retrieve a public key for signed token by a asymetric key algorithm
- **/oauth/check_token**: The endpoint to validate a token (aka introspection endpoint)

### Test authentication

To test authentication you can use Postman. Start the server and send a POST request to the **/oauth/token** endpoint  
You also have to set the _clientId_ and _secret_ as basic authentication parameters to the server

```
    http://127.0.0.1:8080/oauth/token?grant_type=password&username=john&password=12345&scope=read
```

For the password grant type you have to provide the following request parameter

| Parameter | Description | Mandatory |
| --------- | ----------- | --------- |
| grant_type| name of the grant type  | YES       |
| username  | username  | YES       |
| password  | password  | YES       |
| scope     | authentication scope of the client    | YES       |

After executing that request you get a json object with the token. This token is opaque and just used to identify the
authenticated user. There is no data provided with this token as it's possible with JsonWebTokens.
Here it's a simple uuid assigned to the authentication.
The token  has a type (here **bearer**) and expires after a given time in second and you can use this token to access the
resource server. By providing the token to the resource server it allows you to do all the things that are allowed to done
in the scope of this token.

```json
{
    "access_token": "37c94c18-486f-46c5-9223-201425228163",
    "token_type": "bearer",
    "expires_in": 43117,
    "scope": "read"
}
```

The bearer token is directly provided to any client that knows the **clientId** and **secret** and the
**username** and **password** of the user. So this grant type should not be used any more and will be deprecated in OAuth 2.1

### Authorization code grant type

With the Authorization code grant type the user tries to access a secured resource and will be redirected to an
authoriziaton server. Redirection is done from the client via the user to the Authorzation server, where the user
can login, e.g. by providing a username and a password.
But how can the authorization server provide a token to the client? This has to be done by a **redirect URI** that can be
configured in the client details.

The Authorization code grant type is more secure than the passwort grant type

![Authorization Code Grandtype](images/oauth2-granttype-authorization_code.png)

### Configuration of Authorization Server for Authorization Code Grant Type

To enable AuthCode Grant Type you have to provide another ClientDetails for this grant type.
In additon to the Passwortd Grant Type the redirectUris should point to the client for exchange of the auth code with a
token.

```java
    clients.inMemory()
        .withClient("client2")
        .secret("secret2")
        .scopes("read")
        .authorizedGrantTypes("authorization_code")
        .redirectUris("http://localhost:9090");
```

Also the auth server application should provide a login form so the  user can enter its credentials.
Therefore the `configure(HttpSecurity)` method of the WebSecurityConfigurerAdapter has to be overridden as below.

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin();
        http.authorizeRequests().anyRequest().authenticated();
    }
```

#### Step 1: opens a login page

```
http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=client2&scope=read&redirect_uri=http://localhost:8080/auth-code
```

Open the link in a browser and login with your credentials. After authentication you will see a consent page. When consent is given
by the user / resource owner, then the Authorization Code is sent to the client address defined in the redirect url.

|   parameter   | value | required |
|---------------|------ | -------- |
| response_type | **code**  |  YES |
| client_id     | the client id as defined | YES | 
| scope         | the requested scope | YES (?) |
| redirect_uri  | client url to sent the auth code to | YES |



#### Step 2: exchange auth code for token (Seems to be wrong)
```
http://127.0.0.1:8080/oauth/token?grant_type=authorization_code&scope=read&code={authCode}&redirect_uri=http://localhost:8080/auth-code
```

When the user is authenticated the authorization code is sent to the client and it can exchange the code for a token.
For this request the same parameters have to be provided as for the initial call to the authorization server in Step 1.

The client has also to authenticate with clientId and Secret to the authorziation server.
That makes this grant type more secure than the **Implicit** Grant Type where the access token is directly send to the
client after the user has authenticated with the authorization server.

|   parameter   | value | required |
|---------------|------ | -------- |
| grant_type    | **authorization_code**   |  YES |
| client_id     | the client id as defined | YES | 
| scope         | the requested scope      | YES (?) |
| redirect_uri  | client url from the previous request | YES |


**_NOTE_**

The consent request can be deactivated by setting the scopes to autoApproved

```java
    clients.inMemory()
        .withClient("client2")
        .secret("secret2")
        .scopes("read")
        .autoApproved("read")
        .authorizedGrantTypes("authorization_code")
        .redirectUris("http://localhost:9090");
```

**_ATTENTION_**

For ease of use CSRF is disabled in this example.

[TOC](./README.md)
