package de.gdevelop.spring.security.chapter06.service;

import de.gdevelop.spring.security.chapter06.entites.Otp;
import de.gdevelop.spring.security.chapter06.entites.User;
import de.gdevelop.spring.security.chapter06.repository.OtpRepository;
import de.gdevelop.spring.security.chapter06.repository.UserRepository;
import de.gdevelop.spring.security.chapter06.security.model.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class JpaUserDetailsService implements UserDetailsService
{

    private UserRepository userRepository;
    private OtpRepository otpRepository;

    public JpaUserDetailsService(UserRepository userRepository, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found."));
        return new SecurityUser(user);
    }

    public Otp saveOnetimepassword(Otp otp) {
        return  otpRepository.save(otp);
    }


}