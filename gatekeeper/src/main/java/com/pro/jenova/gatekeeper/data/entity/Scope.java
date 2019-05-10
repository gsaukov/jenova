package com.pro.jenova.gatekeeper.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SCOPE")
public class Scope implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PERMISSIONS")
    private String permissions;

    private Scope() {
        // JPA
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

}

