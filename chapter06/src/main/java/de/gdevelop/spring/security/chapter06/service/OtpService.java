package de.gdevelop.spring.security.chapter06.service;

import de.gdevelop.spring.security.chapter06.entites.Otp;
import de.gdevelop.spring.security.chapter06.repository.OtpRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class OtpService {

    OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public Otp saveOtp(Otp otp) {

        var entity = otpRepository.findByUsername(otp.getUsername()).orElse(otp);
        entity.setOtp(otp.getOtp());
        return otpRepository.save(entity);
    }


    public Otp findByUsername(String username) {

        return otpRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    }

}
