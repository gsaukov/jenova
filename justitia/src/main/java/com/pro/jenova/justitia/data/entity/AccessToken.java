package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "ACCESS_TOKEN")
public class AccessToken extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "USAGE_COUNT")
    private Integer usageCount;

    @Column(name = "MAX_USAGES")
    private Integer maxUsages;

    @Column(name = "PERMISSIONS")
    private String permissions;

    @Column(name = "JTI")
    private String jti;

    @Column(name = "ENCODED")
    private String encoded;

    private AccessToken() {
        // JPA
    }

    private AccessToken(Builder builder) {
        usageCount = builder.usageCount;
        maxUsages = builder.maxUsages;
        permissions = builder.permissions;
        jti = builder.jti;
        encoded = builder.encoded;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Integer getMaxUsages() {
        return maxUsages;
    }

    public void setMaxUsages(Integer maxUsages) {
        this.maxUsages = maxUsages;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
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

        AccessToken user = (AccessToken) o;

        return Objects.equals(jti, user.jti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jti);
    }

    public static final class Builder {

        private Integer usageCount;
        private Integer maxUsages;
        private String permissions;
        private String jti;
        private String encoded;

        public Builder withUsageCount(Integer usageCount) {
            this.usageCount = usageCount;
            return this;
        }

        public Builder withMaxUsages(Integer maxUsages) {
            this.maxUsages = maxUsages;
            return this;
        }

        public Builder withPermissions(String permissions) {
            this.permissions = permissions;
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

        public AccessToken build() {
            return new AccessToken(this);
        }

    }

}

