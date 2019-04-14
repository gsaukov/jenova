package com.pro.jenova.justitia.security.sca;

import com.pro.jenova.justitia.data.entity.Challenge;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.ChallengeRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.isInstanceOf;

public class StrongCustomerAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = getLogger(StrongCustomerAuthenticationProvider.class);

    private LoginRepository loginRepository;
    private ChallengeRepository challengeRepository;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        isInstanceOf(StrongCustomerAuthenticationToken.class, authentication,
                "StrongCustomerAuthenticationProvider only supports StrongCustomerAuthenticationToken");
        StrongCustomerAuthenticationToken authRequest = (StrongCustomerAuthenticationToken) authentication;

        Login login = getLoginIfValid(authRequest);
        UserDetails userDetails = getUserIfValid(authRequest, login);

        challengeRepository.findByLogin(login).forEach(challenge -> check(login, userDetails, challenge, authRequest));

        return createSuccessfulAuthentication(authRequest, userDetails);
    }

    private Authentication createSuccessfulAuthentication(StrongCustomerAuthenticationToken authRequest, UserDetails userDetails) {
        StrongCustomerAuthenticationToken result = new StrongCustomerAuthenticationToken(
                authRequest.getReference(), authRequest.getPrincipal(), userDetails.getAuthorities());
        result.setDetails(authRequest.getDetails());
        return result;
    }

    private void check(Login login, UserDetails userDetails, Challenge challenge,
                       StrongCustomerAuthenticationToken authRequest) {
        switch (challenge.getType()) {
            case CREDENTIALS:
                checkCredentials(login, userDetails, authRequest);
                break;
            case ONE_TIME_PASSWORD:
                checkOneTimePassword(login, challenge, authRequest);
                break;
            case OUT_OF_BAND:
                checkOutOfBand(login, challenge);
                break;
            default:
                logger.debug("Unknown challenge found for login reference {}.", login.getReference());
                throw new BadCredentialsException("Bad credentials.");
        }
    }

    private void checkCredentials(Login login, UserDetails userDetails, StrongCustomerAuthenticationToken authRequest) {
        Object username = authRequest.getUsername();
        Object credentials = authRequest.getCredentials();

        if (username == null || credentials == null) {
            logger.debug("No username/credentials provided for login reference {}.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }

        if (!login.getUsername().equals(username)) {
            logger.debug("Username in login reference {} is no match.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }

        if (!passwordEncoder.matches((String) credentials, userDetails.getPassword())) {
            logger.debug("Credentials in login reference {} is no match.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private void checkOneTimePassword(Login login, Challenge challenge, StrongCustomerAuthenticationToken authRequest) {
        Object oneTimePassword = authRequest.getOneTimePassword();
        if (oneTimePassword == null) {
            logger.debug("No one time password provided for login reference {}.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }

        if (!oneTimePassword.equals(challenge.getOneTimePassword())) {
            logger.debug("One time password for login reference {} is no match.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private void checkOutOfBand(Login login, Challenge challenge) {
        if (!challenge.isCompleted()) {
            logger.debug("Out of band for login reference {} is not completed.", login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    private Login getLoginIfValid(StrongCustomerAuthenticationToken authRequest) {
        String reference = (String) authRequest.getReference();

        Optional<Login> maybeLogin = loginRepository.findByReference(reference);
        if (!maybeLogin.isPresent()) {
            logger.debug("No login found for reference {}.", reference);
            throw new BadCredentialsException("Bad credentials.");
        }

        Login login = maybeLogin.get();
        if (login.isCompleted()) {
            logger.debug("Login reference {} already completed.", reference);
            throw new BadCredentialsException("Bad credentials.");
        }

        if (login.isExpired()) {
            logger.debug("Login reference {} is expired.", reference);
            throw new BadCredentialsException("Bad credentials.");
        }

        return login;
    }

    private UserDetails getUserIfValid(StrongCustomerAuthenticationToken authRequest, Login login) throws AuthenticationException {
        String principal = (String) authRequest.getPrincipal();

        if (!login.getUsername().equals(principal)) {
            logger.debug("Username in login reference {} does not match principal {}.", login.getReference(), principal);
            throw new BadCredentialsException("Bad credentials.");
        }

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(principal);
        } catch (UsernameNotFoundException exc) {
            logger.debug("Username {} in login reference {} not found.", principal, login.getReference());
            throw new BadCredentialsException("Bad credentials.");
        }

        if (!userDetails.isAccountNonExpired() || !userDetails.isCredentialsNonExpired()) {
            logger.debug("Account/credentials for username {} is expired.", principal);
            throw new BadCredentialsException("Bad credentials.");
        }

        if (!userDetails.isAccountNonLocked() || !userDetails.isEnabled()) {
            logger.debug("Account for username {} is disabled/locked.", principal);
            throw new BadCredentialsException("Bad credentials.");
        }

        return userDetails;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (StrongCustomerAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void setChallengeRepository(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

}
