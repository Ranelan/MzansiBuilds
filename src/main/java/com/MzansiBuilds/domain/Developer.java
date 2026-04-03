package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int developer_id;
    private String email;
    private String password;
    private String bio;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Developer() {
    }

    public Developer(int developer_id, String email, String password, String bio, LocalDateTime created_at, LocalDateTime updated_at) {
        this.developer_id = developer_id;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Developer(DeveloperBuilder developerBuilder) {
        this.email = developerBuilder.email;
        this.password = developerBuilder.password;
        this.bio = developerBuilder.bio;
        this.created_at = developerBuilder.created_at;
        this.updated_at = developerBuilder.updated_at;
    }

    public int getDeveloper_id() {
        return developer_id;
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

    @Override
    public String toString() {
        return "Developer{" +
                "developer_id=" + developer_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bio='" + bio + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    public static class DeveloperBuilder {
        private int developer_id;
        private String email;
        private String password;
        private String bio;
        private LocalDateTime created_at;
        private LocalDateTime updated_at;


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
