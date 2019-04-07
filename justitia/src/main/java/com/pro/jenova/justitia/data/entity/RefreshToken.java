package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "ATI")
    private String ati;

    @Column(name = "JTI")
    private String jti;

    @Column(name = "ENCODED")
    private String encoded;

    private RefreshToken() {
        // JPA
    }

    private RefreshToken(Builder builder) {
        ati = builder.ati;
        jti = builder.jti;
        encoded = builder.encoded;
    }

    public String getAti() {
        return ati;
    }

    public void setAti(String ati) {
        this.ati = ati;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getEncoded() {
        return encoded;
    }

    public void setEncoded(String encoded) {
        this.encoded = encoded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RefreshToken user = (RefreshToken) o;

        return Objects.equals(ati, user.ati);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ati);
    }

    public static final class Builder {

        private String ati;
        private String jti;
        private String encoded;

        public Builder withAti(String ati) {
            this.ati = ati;
            return this;
        }

        public Builder withJti(String jti) {
            this.jti = jti;
            return this;
        }

        public Builder withEncoded(String encoded) {
            this.encoded = encoded;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }

    }

}

