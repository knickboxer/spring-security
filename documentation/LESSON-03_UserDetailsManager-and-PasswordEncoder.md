# Spring Security
[TOC](./README.md)

##Lesson 3 - UserDetailsManager and PasswordEncoder

⭐ Timestamps ⭐
- 1:26 Recap of Spring-security Architecture
- 6:45 Creating Spring boot project with Dependencies
- 11:21 Sample Controller
- 14:18 Spring java configuration for UserDetailsService and PasswordEncoder
- 18:07 Using JDBC UserDetailsManager(a type of UserDetailsService), Difference UserDetailsManager vs UserDetailsService
- 29:40 Creating Tables As needed by JDBCUserDetailsManager along with data
- 38:38 Using encrypted/hashed PasswordEncoder
- 41:50 Managing users(like creating user) using UserDetailsManager Contracts and new Controller
- 56:28 Why BCryptEncoder over other hashing algorithm (like MD5)

### Part 1 - Using the default JdbcUserDetailsManager

JdbcUserDetailsManager is a named Manager because of its role to manage users.

In this lesson an instance of `JdbcUserDetailsManager` is used as UserDetailsService and one of `NoOpPasswordEncoder`
as PasswordEncoder.

`JdbcUserDetailsManager` requires a database table name "users" with at least three columns "username", "password", and
"enabled" Flag and authorities table with "username", and "authority" column to assign authorities to a user.

### Part 2 - Using an alternative PasswordEncoder

To be able to use another passwordEncoder the data from the database has to be created with an encrypted password.
This can be done by disabling CSRF and create a user by an unsecured endpoint

To create a user with bcrypt password CSRF has to be disabled and the /user endpoint shouldn't be secured
You also have to extend the `WebSecurityConfigurerAdapter`. These changes apply to the `ProjectConfiguration`

[TOC](./README.md)
