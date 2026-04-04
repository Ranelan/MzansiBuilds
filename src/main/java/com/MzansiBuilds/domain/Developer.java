package com.MzansiBuilds.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int developerId;
    private String username;
    private String email;
    private String password;
    private String bio;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Developer() {
    }

    public Developer(int developerId, String username, String email, String password, String bio, LocalDateTime created_at, LocalDateTime updated_at) {
        this.developerId = developerId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Developer(DeveloperBuilder developerBuilder) {
        this.email = developerBuilder.email;
        this.username = developerBuilder.username;
        this.password = developerBuilder.password;
        this.bio = developerBuilder.bio;
        this.created_at = developerBuilder.created_at;
        this.updated_at = developerBuilder.updated_at;
    }

    public int getDeveloperId() {
        return developerId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        if (created_at == null) {
            created_at = now;
        }
        updated_at = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Developer{" +
                "developer_id=" + developerId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    public static class DeveloperBuilder {
        private String username;
        private String email;
        private String password;
        private String bio;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;

        public DeveloperBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public DeveloperBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public DeveloperBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public DeveloperBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public DeveloperBuilder setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
            return this;
        }

        public DeveloperBuilder setUpdated_at(LocalDateTime updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public DeveloperBuilder copy(Developer developer) {
            this.username = developer.username;
            this.email = developer.email;
            this.password = developer.password;
            this.bio = developer.bio;
            this.created_at = developer.created_at;
            this.updated_at = developer.updated_at;
            return this;
        }

        public Developer build() {
            return new Developer(this);
        }
    }
}