package com.pro.jenova.gatekeeper.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ACCESS_TOKEN")
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}
