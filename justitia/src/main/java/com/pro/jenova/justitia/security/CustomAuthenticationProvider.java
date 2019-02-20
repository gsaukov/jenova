package com.pro.jenova.justitia.security;

import com.pro.jenova.justitia.data.entity.LoginRequest;
import com.pro.jenova.justitia.data.entity.LoginVerification;
import com.pro.jenova.justitia.data.repository.LoginRequestRepository;
import com.pro.jenova.justitia.data.repository.LoginVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private LoginRequestRepository loginRequestRepository;

    @Autowired
    private LoginVerificationRepository loginVerificationRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        CustomWebAuthenticationDetails customWebAuthenticationDetails =
                ((CustomWebAuthenticationDetails) authentication.getDetails());

        String reference = customWebAuthenticationDetails.getReference();

        return authenticate(getValidLoginRequest(reference), authentication, customWebAuthenticationDetails);
    }

    private Authentication authenticate(LoginRequest loginRequest, Authentication authentication,
                                        CustomWebAuthenticationDetails customWebAuthenticationDetails) {
        List<LoginVerification> loginVerifications = loginVerificationRepository.findByLoginRequest(loginRequest);

        if (loginVerifications.isEmpty()) {
            throw new IllegalArgumentException("login verifications cannot be empty");
        }

        verifyOneTimePassword(loginVerifications, customWebAuthenticationDetails);
        verifyOutOfBand(loginVerifications);

        return verifyUsernameAndPassword(loginRequest, authentication, loginVerifications);
    }

    private Authentication verifyUsernameAndPassword(LoginRequest loginRequest, Authentication authentication,
                                                     List<LoginVerification> loginVerifications) {
        Optional<LoginVerification> usernamePassword = loginVerifications.stream().filter(
                candidate -> candidate.getMethod().equals(LoginVerification.Method.USERNAME_PASSWORD)).findFirst();

        if (usernamePassword.isPresent()) {
            return super.authenticate(authentication);
        }

        UserDetails userDetails = getUserDetailsService().loadUserByUsername(loginRequest.getUsername());

        return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),
                userDetails.getAuthorities());

    }

    private void verifyOneTimePassword(List<LoginVerification> loginVerifications,
                                       CustomWebAuthenticationDetails customWebAuthenticationDetails) {
        String oneTimePassword = customWebAuthenticationDetails.getOneTimePassword();

        boolean isValid = loginVerifications.stream()
                .filter(candidate -> candidate.getMethod().equals(LoginVerification.Method.ONE_TIME_PASSWORD))
                .allMatch(candidate -> candidate.getValue().equals(oneTimePassword));

        if (!isValid) {
            throw new BadCredentialsException("Invalid one time password.");
        }
    }

    private void verifyOutOfBand(List<LoginVerification> loginVerifications) {
        boolean isValid = loginVerifications.stream()
                .filter(candidate -> candidate.getMethod().equals(LoginVerification.Method.OUT_OF_BAND))
                .allMatch(candidate -> candidate.getValue().equals("DONE"));

        if (!isValid) {
            throw new BadCredentialsException("Out of band incomplete.");
        }
    }

    private LoginRequest getValidLoginRequest(String reference) {
        if (reference == null) {
            throw new BadCredentialsException("Reference not provided.");
        }

        Optional<LoginRequest> loginRequest = loginRequestRepository.findByReference(reference);

        if (!loginRequest.isPresent()) {
            throw new BadCredentialsException("Login request not found.");
        }

        if (loginRequest.get().isExpired()) {
            throw new BadCredentialsException("Login request is expired.");
        }

        if (!loginRequest.get().isPending()) {
            throw new BadCredentialsException("Login request invalid status.");
        }

        return loginRequest.get();
    }

}
