package com.pro.jenova.justitia.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "SCOPE")
public class Scope extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MAX_USAGES")
    private Integer maxUsages;

    @Column(name = "PERMISSIONS")
    private String permissions;

    private Scope() {
        // JPA
    }

    private Scope(Builder builder) {
        clientId = builder.clientId;
        name = builder.name;
        maxUsages = builder.maxUsages;
        permissions = builder.permissions;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        return Objects.equals(clientId, scope.clientId) && Objects.equals(name, scope.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, name);
    }

    public static final class Builder {

        private String clientId;
        private String name;
        private Integer maxUsages;
        private String permissions;

        public Builder withClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
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

        public Scope build() {
            return new Scope(this);
        }

    }

}

