# Spring Security
[TOC](./README.md)

##Lesson 2 - Implementing a UserDetailService

⭐ Timestamps ⭐
- 1:28 diagram
- 9:40 start coding
- 16:13 creating database tables
- 27:35 creating configuration class
- 30:13 implementation of UserDetailService
- 39:51 creating user that implements UserDetails


Created a `ProjectConfiguration`  with `UserDetailsService` and `PasswordEncoder` bean
`UserDetailsService` is implemented by a `JPAUserDetailsService`.
The Service implements `UserDetailsService` and reads a User entity from the h2 database by its username.
There it uses UserDetailsRepository (a JpaRepository<User, Long>).
The user entity is wrapped by `AuthenticatedUser`. This wrapper provides the UserDetails contract.

`AuthenticatedUser` return the name and the password of the wrapped user object.

To get rid of the PasswordEncoder bean in the ProjectConfiguration it's possible to
add the password encryptionId as a prefix to the users password in the `AuthenticatedUser.getPassword()` method.

[TOC](./README.md)

