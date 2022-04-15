# Spring Security
[TOC](./README.md)

## Lesson 12 - Deeply understanding the grant types

⭐ Timestamps ⭐
- 5:21 Password Grant type Sequence Diagram
- 14:00 QA on OpenID connect
- 22:00 Authorization Code Grant type Sequence Diagram
- 37:00 Client Credentials Grant type Sequence Diagram
- 44:00 Refresh Token- Solution for expired access_token
- 55:00 Simple changes to replace opaque with jwt token


In this lesson all the necessary grant types are explained and coded. While **authorization code** and  **password (resource owner)**
grant types have already been implemented in the previous lesson, here the configuration for
the **client credentials** grant type and the **refresh token** grant type are explained.

#### Client Credentials grant type

The client credentials grant type allows a service to request a token to access a resource server.
A good example for this is, when a environmental service like in kubernetes is executing a readyness probe or a liveness
probe on an applicaton to see if it is already (readyness) / still (liveness) working.

There is no user involed in this process So it should only be used in secured environments.

![Client Credentials Grand type](images/oauth2-granttype-client_credentials-sequence.png)

To enable Client Credentials Grant Type you have to provide another ClientDetails for this grant type.
This client is similar to the Password Grant Type, except the authorizedGrantType() settings.

```java
    clients.inMemory()
        .withClient("client2")
        .secret("secret2")
        .scopes("read")
        .authorizedGrantTypes("client_credentials");
```

To get a token for the client you have to post a request to the authorization server.
You also have to provide the _clientId_ and _secret_ as basic authentication parameters to the server call.

Step 1: post
```
http://127.0.0.1:8080/oauth/authorize?grant_type=client_credentials&scope=read
```

#### Refresh Token Grant Type

The Refresh Token Grant Type allows to get a new token from the Authorization Server in case the used token is going to
expire. This Grant Type expects an existing token and only makes sense with a grant type where a user is involved, like **Password Grant Type** or
**Authorization Code Grant Type**.

To enable Client Credentials Grant Type you have to enhance an existing ClientDetails Grant Type simply by adding
"refresh_token" to the `authorizedGrantTypes()` settings.

```java
    clients.inMemory()
            .withClient("client2")
            .secret("secret2")
            .scopes("read")
            .authorizedGrantTypes("password", "refresh_token");
```

You also have to configure the UserDataService for the endpooint because it is not taken from the context, even when the
UserDetailsService is already provided as a bean. !!!

```java
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
```

When you authorize with and authorization server and the client also has the Refresh Token Grant Type, then the
response will contain a **Refresh Token**.




```json
{
    "access_token": "5a9bbbba-0e30-43e5-a343-e4386a7b666b",
    "token_type": "bearer",
    "refresh_token": "6720220c-342a-40b7-9005-a89cad0e51b8",
    "expires_in": 43200,
    "scope": "read"
}
```

For token refresh you have to provide data

#### Step 1: exchange refresh token for a new  token
```
http://127.0.0.1:8080/oauth/token?grant_type=refresh_token&scope=read&refresh_token=720220c-342a-40b7-9005-a89cad0e51b8
```

When the user is authenticated the authorization code is sent to the client and it can exchange the code for a token.
For this request the same parameters have to be provided as for the initial call to the authorization server in Step 1.

The client has also to authenticate with clientId and Secret to the authorziation server.
That makes this grant type more secure than the **Implicit** Grant Type where the access token is directly send to the
client after the user has authenticated with the authorization server.

|   parameter   | value | required |
|---------------|------ | -------- |
| grant_type    | **refresh_token**   |  YES |
| scope         | the requested scope      | YES |
| refresh_token  | the refresh token provided by the authorization request | YES |



### Replacing the opaque token with a JWT token  --- JWT Teaser

To replace the te opaque token with a JWT token, you only have to set a JwtAccessTokenConverter().
By providing  the Jwt access token converter as Bean Spring will automatically initialize a JwtTokenStore as TokenStore
instance and replace the default tokenstore. By default an `InMemoryTokenStore` is used. With that store a  
`DefaultAuthenticationKeyGenerator` will be instanciated, that creates an MD5 has from username, clientId and the requested
scopes of the authenticated user (Authentication object).

The TokenStore and the AccessTokenConverter can be provided as Beans to the Context and should be supplied to the
`AuthorizationServerEndpointsConfigurer`.

```java
  
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(converter());
    }

    @Bean
    public JwtAccessTokenConverter converter(){
        return new JwtAccessTokenConverter();
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore())
                .accessTokenConverter(converter());
    }

```

[TOC](./README.md)
