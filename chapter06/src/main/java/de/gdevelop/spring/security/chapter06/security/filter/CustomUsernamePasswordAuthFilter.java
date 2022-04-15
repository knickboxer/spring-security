package de.gdevelop.spring.security.chapter06.security.filter;

import de.gdevelop.spring.security.chapter06.entites.Otp;
import de.gdevelop.spring.security.chapter06.security.authentication.OtpAuthentication;
import de.gdevelop.spring.security.chapter06.security.authentication.UsernamePasswordAuthentication;
import de.gdevelop.spring.security.chapter06.service.OtpService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

/**
 * @author Gerhard
 * @project security
 * @created 2022
 */
@Component
public class CustomUsernamePasswordAuthFilter extends OncePerRequestFilter {

   private AuthenticationManager authenticationManager;

   private OtpService otpService;

    public CustomUsernamePasswordAuthFilter(@Lazy AuthenticationManager authenticationManager, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.otpService = otpService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        // Step 1: username & password
        if (otp == null) {
            if (authenticateWithPassword(username, password)) {
                response.setHeader("Authorization", "OTP");
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }

        } else { // Step 2: username /otp;
            if (authenticateWithOtp(response, username, otp)) {
                response.setHeader("Authorization", UUID.randomUUID().toString());
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    private boolean authenticateWithPassword(String username, String password) {
        //Authentication authentication = getAuthenticationFromAuthorizeRequest(request);
        var authentication = new UsernamePasswordAuthentication(username, password);
        try {
            var authenticated = authenticationManager.authenticate(authentication);
            var otpEntity = otpService.saveOtp(createOtp(username));
            logger.info(String.format("%nSecurity token: %s%n", otpEntity.getOtp()));
            return true;
        } catch (AuthenticationException e) {
            logger.info(String.format("%nAuthentication failed: %s->%s%n", e.getClass().toString(), e.getMessage()));
            return false;
        }
    }

    private boolean authenticateWithOtp(HttpServletResponse response, String username, String otp) {
        var authentication = new OtpAuthentication(username, otp);
        try {
            var authenticated = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException e) {
            logger.info(String.format("%nAuthentication failed: %s -> %s%n", e.getClass().toString(), e.getMessage()));
            return false;
        }
    }

    private Otp createOtp(String username) {
        var otp = new Otp();
        otp.setUsername(username);
        otp.setOtp(UUID.randomUUID().toString());
        otp.setIssued(Instant.now());
        return otp;
    }

    private Authentication getAuthenticationFromAuthorizeRequest(HttpServletRequest request) {
        var authorization = request.getHeader("Authorization");
        var decoded = Arrays.toString(Base64.getDecoder().decode(authorization));
        var userAndPassword = decoded.split(":");
        return new UsernamePasswordAuthentication(userAndPassword[0], userAndPassword[1]);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
