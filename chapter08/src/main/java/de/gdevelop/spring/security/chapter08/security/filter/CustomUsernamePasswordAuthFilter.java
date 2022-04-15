package de.gdevelop.spring.security.chapter08.security.filter;

import de.gdevelop.spring.security.chapter08.entites.Otp;
import de.gdevelop.spring.security.chapter08.security.authentication.OtpAuthentication;
import de.gdevelop.spring.security.chapter08.security.authentication.UsernamePasswordAuthentication;
import de.gdevelop.spring.security.chapter08.security.store.CustomTokenStore;
import de.gdevelop.spring.security.chapter08.service.OtpService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.UUID;

@Component
public class CustomUsernamePasswordAuthFilter extends OncePerRequestFilter {

   private final AuthenticationManager authenticationManager;

   private final OtpService otpService;

   CustomTokenStore customTokenStore;

    public CustomUsernamePasswordAuthFilter(@Lazy AuthenticationManager authenticationManager,
                                            OtpService otpService, CustomTokenStore store) {
        this.authenticationManager = authenticationManager;
        this.otpService = otpService;
        this.customTokenStore = store;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        var username = request.getHeader("username");
        var password = request.getHeader("password");
        var otp = request.getHeader("otp");

        // Step 1: authentication with username & password
        if (otp == null) {
            Authentication authentication = authenticateWithPassword(username, password);
            if (authentication != null) {
                var otpEntity = otpService.saveOtp(createOtp(username));
                logger.info(String.format("%nSecurity token: %s%n", otpEntity.getOtp()));

                response.setHeader("OTP", otpEntity.getOtp());

            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }


        } else { // Step 2: authentication with username & otp
            var authentication = authenticateWithOtp(username, otp);
            if (authentication != null) {
                var token = UUID.randomUUID().toString();
                customTokenStore.addToken(token, username);
                response.setHeader("Authorization", token);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
    }

    private Authentication authenticateWithPassword(String username, String password) {
        var authentication = new UsernamePasswordAuthentication(username, password);
        try {
            return authenticationManager.authenticate(authentication);
        } catch (AuthenticationException e) {
            logger.info(String.format("%nAuthentication failed: %s->%s%n", e.getClass().toString(), e.getMessage()));
            return null;
        }
    }

    private Authentication authenticateWithOtp(String username, String otp) {
        var authentication = new OtpAuthentication(username, otp);
        try {
            var authenticated = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authenticated;
        } catch (AuthenticationException e) {
            logger.info(String.format("%nAuthentication failed: %s -> %s%n", e.getClass().toString(), e.getMessage()));
            return null;
        }
    }

    private Otp createOtp(String username) {
        var otp = new Otp();
        otp.setUsername(username);
        otp.setOtp(UUID.randomUUID().toString());
        otp.setIssued(Instant.now());
        return otp;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}
