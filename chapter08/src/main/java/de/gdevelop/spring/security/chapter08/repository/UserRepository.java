package de.gdevelop.spring.security.chapter08.repository;

import de.gdevelop.spring.security.chapter08.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
