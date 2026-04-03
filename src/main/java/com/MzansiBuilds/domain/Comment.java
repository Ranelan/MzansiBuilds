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
    private LocalDateTime created_at;

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
    public Comment(int comment_id, String content, LocalDateTime created_at, Developer developer, Project project) {
        this.comment_id = comment_id;
        this.content = content;
        this.created_at = created_at;
        this.developer = developer;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public Comment(CommentBuilder commentBuilder) {
        this.content = commentBuilder.content;
        this.created_at = commentBuilder.created_at;
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

    public LocalDateTime getCreated_at() {
        return created_at;
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
                ", created_at=" + created_at +
                ", developer=" + developer +
                ", project=" + project +
                '}';
    }

    public static class CommentBuilder {
        private String content;
        private LocalDateTime created_at;
        private Developer developer;
        private Project project;

        public CommentBuilder setContent(String content) {
            this.content = content;
            return this;
        }

        public CommentBuilder setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
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
