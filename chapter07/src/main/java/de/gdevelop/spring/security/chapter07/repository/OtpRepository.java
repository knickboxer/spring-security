package de.gdevelop.spring.security.chapter07.repository;

import de.gdevelop.spring.security.chapter07.entites.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<Otp> findByUsername(String username);
}
