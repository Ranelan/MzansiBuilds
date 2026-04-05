package com.MzansiBuilds.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Developer() {
    }

    public Developer(int developerId, String username, String email, String password, String bio, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.developerId = developerId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Developer(DeveloperBuilder developerBuilder) {
        this.developerId = developerBuilder.developerId;
        this.email = developerBuilder.email;
        this.username = developerBuilder.username;
        this.password = developerBuilder.password;
        this.bio = developerBuilder.bio;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public String toString() {
        return "Developer{" +
                "developer_id=" + developerId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt +
                '}';
    }

    public static class DeveloperBuilder {
        private int developerId;
        private String username;
        private String email;
        private String password;
        private String bio;

        public DeveloperBuilder setDeveloperId(int developerId) {
            this.developerId = developerId;
            return this;
        }

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


        public DeveloperBuilder copy(Developer developer) {
            this.developerId = developer.developerId;
            this.username = developer.username;
            this.email = developer.email;
            this.password = developer.password;
            this.bio = developer.bio;
            return this;
        }

        public Developer build() {
            return new Developer(this);
        }
    }
}