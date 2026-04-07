package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int milestoneId;
    private String title;
    private String description;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime achievedAt;

    //Relationship with Project, Many milestones can be associated with one project.
    @ManyToOne
    private Project project;

    //Default constructor for JPA.
    public Milestone() {
    }

    //Constructor that takes all fields as parameters.
    public Milestone(int milestoneId, String title, String description, LocalDateTime achievedAt, Project project) {
        this.milestoneId = milestoneId;
        this.title = title;
        this.description = description;
        this.achievedAt = achievedAt;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public Milestone(MilestoneBuilder milestoneBuilder) {
        this.milestoneId = milestoneBuilder.milestoneId;
        this.title = milestoneBuilder.title;
        this.description = milestoneBuilder.description;
        this.achievedAt = milestoneBuilder.achievedAt;
        this.project = milestoneBuilder.project;
    }

    //Getters for all fieldS.
    public int getMilestoneId() {
        return milestoneId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAchievedAt() {
        return achievedAt;
    }

    public Project getProject() {
        return project;
    }

    //toString method for easy debugging and logging.
    @Override
    public String toString() {
        return "Milestone{" +
                "milestone_id=" + milestoneId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", achieved at=" + achievedAt +
                ", project=" + project +
                '}';
    }

    public static class MilestoneBuilder {
        private int milestoneId;
        private String title;
        private String description;
        private LocalDateTime achievedAt;
        private Project project;

        public MilestoneBuilder setMilestoneId(int milestoneId) {
            this.milestoneId = milestoneId;
            return this;
        }

        public MilestoneBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MilestoneBuilder setDescription(String description) {
            this.description = description;
            return this;
        }


        public MilestoneBuilder setProject(Project project) {
            this.project = project;
            return this;
        }

        public MilestoneBuilder setAchievedAt(LocalDateTime achievedAt) {
            this.achievedAt = achievedAt;
            return this;
        }

        //Copy method to be used for updating, Allowing to modify only the fields that need to be updated while keeping the rest of the data intact.
        public MilestoneBuilder copy(Milestone milestone) {
            this.milestoneId = milestone.milestoneId;
            this.title = milestone.title;
            this.description = milestone.description;
            this.achievedAt = milestone.achievedAt;
            this.project = milestone.project;
            return this;
        }

        public Milestone build() {
            return new Milestone(this);
        }
    }
}
