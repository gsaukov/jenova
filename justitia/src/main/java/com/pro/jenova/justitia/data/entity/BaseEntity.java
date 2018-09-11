package com.pro.jenova.justitia.data.entity;

import org.springframework.security.core.Authentication;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import static com.pro.jenova.common.util.IdUtils.uuid;
import static java.time.LocalDateTime.now;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Long version;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_UPDATED")
    private LocalDateTime lastUpdated;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @PrePersist
    private void prePersist() {
        id = uuid();

        String username = username();

        createdBy = username;
        updatedBy = username;

        LocalDateTime now = now();

        createdAt = now;
        lastUpdated = now;
    }

    @PreUpdate
    private void preUpdate() {
        updatedBy = username();

        lastUpdated = now();
    }

    protected String username() {
        Authentication authentication = getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return authentication.getName();
        }

        return "anonymous";
    }

}

