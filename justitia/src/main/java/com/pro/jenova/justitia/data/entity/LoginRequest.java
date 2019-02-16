package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "LOGIN_REQUEST")
public class LoginRequest extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "REFERENCE")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @ElementCollection
    @MapKeyColumn(name = "NAME")
    @Column(name = "VALUE")
    @CollectionTable(name = "LOGIN_REQUEST_ATTRS", joinColumns = @JoinColumn(name = "LOGIN_REQUEST_ID"))
    private Map<String, String> attributes;

    private LoginRequest() {
        // JPA
    }

    private LoginRequest(Builder builder) {
        username = builder.username;
        reference = builder.reference;
        status = builder.status;
        expiresAt = builder.expiresAt;
        attributes = builder.attributes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LoginRequest user = (LoginRequest) o;

        return Objects.equals(reference, user.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference);
    }

    public enum Status {
        PENDING,
        COMPLETED,
        INVALIDATED
    }

    public static final class Builder {

        private String username;
        private String reference;
        private Status status;
        private LocalDateTime expiresAt;
        private Map<String, String> attributes = new HashMap<>();

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withExpiresAt(LocalDateTime expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public Builder withAttribute(String name, String value) {
            this.attributes.put(name, value);
            return this;
        }

        public LoginRequest build() {
            return new LoginRequest(this);
        }

    }

}

