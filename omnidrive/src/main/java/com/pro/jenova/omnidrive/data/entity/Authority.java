package com.pro.jenova.omnidrive.data.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AUTHORITY")
public class Authority extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "ROLE")
    private String role;

    private Authority() {
        // JPA
    }

    private Authority(Builder builder) {
        user = builder.user;
        role = builder.role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Authority authority = (Authority) o;

        return Objects.equals(user, authority.user) && Objects.equals(role, authority.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, role);
    }

    public static final class Builder {

        private User user;
        private String role;

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withRole(String role) {
            this.role = role;
            return this;
        }

        public Authority build() {
            return new Authority(this);
        }

    }

}

