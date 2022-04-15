package de.gdevelop.spring.security.chapter07.security.provider;

import de.gdevelop.spring.security.chapter07.entites.Otp;
import de.gdevelop.spring.security.chapter07.security.authentication.OtpAuthentication;
import de.gdevelop.spring.security.chapter07.service.OtpService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomOtpAuthenticationProvider implements AuthenticationProvider {

    private OtpService otpService;

    public CustomOtpAuthenticationProvider(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        var otp = authentication.getCredentials().toString();

        Otp otpEntity= otpService.findByUsername(username);

        if (otpEntity.getOtp().equals(otp)) {
            if (otpEntity.getIssued().plusSeconds(30).isAfter(Instant.now())) {
                return new UsernamePasswordAuthenticationToken(username, otp);
            }
            throw new BadCredentialsException("OTP Time exceeded");
        }

        throw new BadCredentialsException("Wrong creds!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
