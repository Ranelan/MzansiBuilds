package com.MzansiBuilds.domain;

import com.MzansiBuilds.enums.RequestStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class CollaborationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    private String message;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    //Relationship with Developer, Many collaboration requests can be sent by one developer.
    @ManyToOne
    @JoinColumn(name = "developerId")
    private Developer developer;

    //Relationship with Project, Many collaboration requests can be associated with one project.
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    //Default constructor for JPA.
    public CollaborationRequest() {

    }

    //Constructor that takes all fields as parameters.
    public CollaborationRequest(int requestId, String message, LocalDateTime createdAt, RequestStatus status, Developer developer, Project project) {
        this.requestId = requestId;
        this.message = message;
        this.createdAt = createdAt;
        this.status = status;
        this.developer = developer;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public CollaborationRequest(CollaborationRequestBuilder collaborationRequestBuilder) {
        this.requestId = collaborationRequestBuilder.requestId;
        this.message = collaborationRequestBuilder.message;
        this.createdAt = collaborationRequestBuilder.createdAt;
        this.status = collaborationRequestBuilder.status == null
                ? RequestStatus.PENDING
                : collaborationRequestBuilder.status;
        this.developer = collaborationRequestBuilder.developer;
        this.project = collaborationRequestBuilder.project;
    }

    //Getters for all fields.
    public int getRequestId() {
        return requestId;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
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
                "request_id=" + requestId +
                ", message='" + message + '\'' +
                ", created_at=" + createdAt +
                ", status=" + status +
                ", developer=" + developer +
                ", project=" + project +
                '}';
    }

    //Builder class for CollaborationRequest, allowing for flexible and readable object creation.
    public static class CollaborationRequestBuilder {
        private int requestId;
        private String message;
        private LocalDateTime createdAt;
        private RequestStatus status = RequestStatus.PENDING;
        private Developer developer;
        private Project project;

        public CollaborationRequestBuilder setRequestId(int requestId) {
            this.requestId = requestId;
            return this;
        }

        public CollaborationRequestBuilder setMessage(String message) {
            this.message = message;
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

        public CollaborationRequestBuilder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        //Copy method to be used for updating, Allowing to modify only the fields that need to be updated while keeping the rest of the data intact.
        public CollaborationRequestBuilder copy(CollaborationRequest collaborationRequest) {
            this.requestId = collaborationRequest.requestId;
            this.message = collaborationRequest.message;
            this.createdAt = collaborationRequest.createdAt;
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
