package com.pro.jenova.justitia.security.sca;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class StrongCustomerAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private final Object reference;
    private final Object principal;
    private Object username;
    private Object credentials;
    private Object oneTimePassword;

    public StrongCustomerAuthenticationToken(Object reference, Object principal) {
        super(null);
        this.reference = reference;
        this.principal = principal;
        setAuthenticated(false);
    }

    public StrongCustomerAuthenticationToken(Object reference, Object principal,
                                             Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.reference = reference;
        this.principal = principal;
        super.setAuthenticated(true);
    }


    public Object getReference() {
        return reference;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public Object getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(Object oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        username = null;
        credentials = null;
        oneTimePassword = null;
    }

}
