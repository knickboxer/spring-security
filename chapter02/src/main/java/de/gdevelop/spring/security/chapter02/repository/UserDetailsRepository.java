package de.gdevelop.spring.security.chapter02.repository;

import de.gdevelop.spring.security.chapter02.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public interface UserDetailsRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
