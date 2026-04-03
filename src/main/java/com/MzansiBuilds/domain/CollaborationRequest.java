package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class CollaborationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int request_id;
    private String message;
    @CreationTimestamp
    private LocalDateTime created_at;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    //Relationship with Developer, Many collaboration requests can be sent by one developer.
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    //Relationship with Project, Many collaboration requests can be associated with one project.
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    //Default constructor for JPA.
    public CollaborationRequest() {

    }

    //Constructor that takes all fields as parameters.
    public CollaborationRequest(int request_id, String message, LocalDateTime created_at, RequestStatus status, Developer developer, Project project) {
        this.request_id = request_id;
        this.message = message;
        this.created_at = created_at;
        this.status = status;
        this.developer = developer;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public CollaborationRequest(CollaborationRequestBuilder collaborationRequestBuilder) {
        this.message = collaborationRequestBuilder.message;
        this.created_at = collaborationRequestBuilder.created_at;
        this.status = collaborationRequestBuilder.status;
        this.developer = collaborationRequestBuilder.developer;
        this.project = collaborationRequestBuilder.project;
    }

    //Getters for all fields.
    public int getRequest_id() {
        return request_id;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public RequestStatus getStatus() {
        return status;
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
        return "CollaborationRequest{" +
                "request_id=" + request_id +
                ", message='" + message + '\'' +
                ", created_at=" + created_at +
                ", status=" + status +
                ", developer=" + developer +
                ", project=" + project +
                '}';
    }

    //Builder class for CollaborationRequest, allowing for flexible and readable object creation.
    public static class CollaborationRequestBuilder {
        private String message;
        private LocalDateTime created_at;
        private RequestStatus status;
        private Developer developer;
        private Project project;

        public CollaborationRequestBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public CollaborationRequestBuilder setCreated_at(LocalDateTime created_at) {
            this.created_at = created_at;
            return this;
        }

        public CollaborationRequestBuilder setStatus(RequestStatus status) {
            this.status = status;
            return this;
        }

        public CollaborationRequestBuilder setDeveloper(Developer developer) {
            this.developer = developer;
            return this;
        }

        public CollaborationRequestBuilder setProject(Project project) {
            this.project = project;
            return this;
        }

        //Copy method to be used for updating, Allowing to modify only the fields that need to be updated while keeping the rest of the data intact.
        public CollaborationRequestBuilder copy(CollaborationRequest collaborationRequest) {
            this.message = collaborationRequest.message;
            this.created_at = collaborationRequest.created_at;
            this.status = collaborationRequest.status;
            this.developer = collaborationRequest.developer;
            this.project = collaborationRequest.project;
            return this;
        }

        public CollaborationRequest build() {
            return new CollaborationRequest(this);
        }
    }
}
