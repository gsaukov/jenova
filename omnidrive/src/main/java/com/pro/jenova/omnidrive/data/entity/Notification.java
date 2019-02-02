package com.pro.jenova.omnidrive.data.entity;

import com.pro.jenova.common.data.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "ROUTE")
    private String route;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    private Notification() {
        // JPA
    }

    private Notification(Builder builder) {
        route = builder.route;
        content = builder.content;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Notification notification = (Notification) o;

        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public static final class Builder {

        private String route;
        private String content;

        public Builder withRoute(String route) {
            this.route = route;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Notification build() {
            return new Notification(this);
        }

    }

}

