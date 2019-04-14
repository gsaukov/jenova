package com.pro.jenova.justitia.security.sca;

import com.pro.jenova.common.data.audit.DatabaseMetricsCollector;
import com.pro.jenova.justitia.data.entity.Login;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import org.slf4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static org.apache.commons.lang.Validate.notNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.StringUtils.isEmpty;

public class StrongCustomerAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = getLogger(StrongCustomerAuthenticationFilter.class);

    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String SCA_HEADER = "sca";
    private static final String OTP_HEADER = "otp";
    private static final String BASIC_PREFIX = "basic ";

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    private AuthenticationManager authenticationManager;
    private LoginRepository loginRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String reference = request.getHeader(SCA_HEADER);

        if (isEmpty(reference)) {
            logger.debug("No SCA header found so skipping.");
            chain.doFilter(request, response);
            return;
        }

        try {
            handle(request, response, reference);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, String reference) {
        Optional<Login> maybeLogin = loginRepository.findByReference(reference);

        if (maybeLogin.isPresent() && authenticationIsRequired(reference)) {
            Login login = maybeLogin.get();

            StrongCustomerAuthenticationToken authRequest = new StrongCustomerAuthenticationToken(
                    login.getReference(), login.getUsername());

            enrichWithBasicAuthIfPresent(request, authRequest);
            enrichWithOtpIfPresent(request, authRequest);

            authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
            Authentication authResult = this.authenticationManager.authenticate(authRequest);

            SecurityContextHolder.getContext().setAuthentication(authResult);
        }
    }

    private void enrichWithBasicAuthIfPresent(HttpServletRequest request, StrongCustomerAuthenticationToken authRequest) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null || !authorizationHeader.toLowerCase().startsWith(BASIC_PREFIX)) {
            return;
        }

        byte[] base64Token = authorizationHeader.substring(BASIC_PREFIX.length()).getBytes(UTF_8);
        byte[] decoded;
        try {
            decoded = getDecoder().decode(base64Token);
        } catch (IllegalArgumentException exc) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, UTF_8);

        int delimiter = token.indexOf(":");
        if (delimiter == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }

        authRequest.setUsername(token.substring(0, delimiter));
        authRequest.setCredentials(token.substring(delimiter + 1));
    }

    private void enrichWithOtpIfPresent(HttpServletRequest request, StrongCustomerAuthenticationToken authRequest) {
        String oneTimePassword = request.getHeader(OTP_HEADER);

        if (isEmpty(oneTimePassword)) {
            return;
        }

        authRequest.setOneTimePassword(oneTimePassword);
    }

    private boolean authenticationIsRequired(String reference) {
        Authentication existingAuth = SecurityContextHolder.getContext()
                .getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof StrongCustomerAuthenticationToken
                && !((StrongCustomerAuthenticationToken) existingAuth).getReference().equals(reference)) {
            return true;
        }

        if (existingAuth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        notNull(authenticationManager, "AuthenticationManager required");
        this.authenticationManager = authenticationManager;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        notNull(loginRepository, "LoginRepository required");
        this.loginRepository = loginRepository;
    }

    public void setAuthenticationDetailsSource(
            AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        notNull(authenticationDetailsSource, "AuthenticationDetailsSource required");
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

}
