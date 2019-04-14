package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CHALLENGE")
public class Challenge extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "LOGIN_ID")
    private Login login;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "ONE_TIME_PASSWORD")
    private String oneTimePassword;

    private Challenge() {
        // JPA
    }

    private Challenge(Builder builder) {
        login = builder.login;
        status = builder.status;
        type = builder.type;
        oneTimePassword = builder.oneTimePassword;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isCompleted() {
        return Status.COMPLETED.equals(status);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getOneTimePassword() {
        return oneTimePassword;
    }

    public void setOneTimePassword(String oneTimePassword) {
        this.oneTimePassword = oneTimePassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Challenge challenge = (Challenge) o;

        return Objects.equals(login, challenge.login) && Objects.equals(this.type, challenge.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, type);
    }

    public enum Type {
        CREDENTIALS,
        ONE_TIME_PASSWORD,
        OUT_OF_BAND
    }

    public enum Status {
        PENDING,
        COMPLETED,
        NOT_APPLICABLE
    }

    public static final class Builder {

        private Login login;
        private Status status;
        private Type type;
        private String oneTimePassword;

        public Builder withLogin(Login login) {
            this.login = login;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withOneTimePassword(String oneTimePassword) {
            this.oneTimePassword = oneTimePassword;
            return this;
        }

        public Challenge build() {
            return new Challenge(this);
        }

    }

}

