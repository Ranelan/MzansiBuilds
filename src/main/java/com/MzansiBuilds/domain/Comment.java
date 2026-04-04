package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int comment_id;
    private String content;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    //Relationship with Developer, Many comments can be made by one developer.
    @ManyToOne
    private Developer developer;
    //Relationship with Project, Many comments can be associated with one project.
    @ManyToOne
    private Project project;

    //Default constructor for JPA.
    public Comment() {

    }

    //Constructor that takes all fields as parameters.
    public Comment(int comment_id, String content, LocalDateTime createdAt, Developer developer, Project project) {
        this.comment_id = comment_id;
        this.content = content;
        this.createdAt = createdAt;
        this.developer = developer;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public Comment(CommentBuilder commentBuilder) {
        this.content = commentBuilder.content;
        this.developer = commentBuilder.developer;
        this.project = commentBuilder.project;
    }

    //Getters for all fields.

    public int getComment_id() {
        return comment_id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Project getProject() {
        return project;
    }

    //toString method for easy debugging and logging.
    @Override
    public String toString() {
        return "Comment{" +
                "comment_id=" + comment_id +
                ", content='" + content + '\'' +
                ", created_at=" + createdAt +
                ", developer=" + developer +
                ", project=" + project +
                '}';
    }

    public static class CommentBuilder {
        private String content;
        private Developer developer;
        private Project project;

        public CommentBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public CommentBuilder setDeveloper(Developer developer) {
            this.developer = developer;
            return this;
        }

        public CommentBuilder setProject(Project project) {
            this.project = project;
            return this;
        }

        public CommentBuilder copy(Comment comment) {
            this.content = comment.content;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }
}
