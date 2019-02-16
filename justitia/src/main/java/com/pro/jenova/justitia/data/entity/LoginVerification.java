package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LOGIN_VERIFICATION")
public class LoginVerification extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "LOGIN_REQUEST_ID")
    private LoginRequest loginRequest;

    @Enumerated(EnumType.STRING)
    @Column(name = "METHOD")
    private Method method;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "VALUE")
    private String value;

    private LoginVerification() {
        // JPA
    }

    private LoginVerification(Builder builder) {
        loginRequest = builder.loginRequest;
        method = builder.method;
        status = builder.status;
        value = builder.value;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }

    public void setLoginRequest(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LoginVerification that = (LoginVerification) o;

        return Objects.equals(loginRequest, that.loginRequest) &&
                method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loginRequest, method);
    }

    public enum Method {
        USERNAME_PASSWORD,
        ONE_TIME_PASSWORD,
        OUT_OF_BAND
    }

    public enum Status {
        PENDING,
        PROVIDED,
        FAILED,
    }

    public static final class Builder {

        private LoginRequest loginRequest;
        private Method method;
        private Status status;
        private String value;

        public Builder withLoginRequest(LoginRequest loginRequest) {
            this.loginRequest = loginRequest;
            return this;
        }

        public Builder withMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public LoginVerification build() {
            return new LoginVerification(this);
        }

    }

}

