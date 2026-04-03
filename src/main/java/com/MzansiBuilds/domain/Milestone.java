package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int milestone_id;
    private String title;
    private String description;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime achieved_at;

    //Relationship with Project, Many milestones can be associated with one project.
    @ManyToOne
    private Project project;

    //Default constructor for JPA.
    public Milestone() {
    }

    //Constructor that takes all fields as parameters.
    public Milestone(int milestone_id, String title, String description, LocalDateTime achieved_at, Project project) {
        this.milestone_id = milestone_id;
        this.title = title;
        this.description = description;
        this.achieved_at = achieved_at;
        this.project = project;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public Milestone(MilestoneBuilder milestoneBuilder) {
        this.title = milestoneBuilder.title;
        this.description = milestoneBuilder.description;
    }

    //Getters for all fieldS.
    public int getMilestone_id() {
        return milestone_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getAchieved_at() {
        return achieved_at;
    }

    public Project getProject() {
        return project;
    }

    //toString method for easy debugging and logging.
    @Override
    public String toString() {
        return "Milestone{" +
                "milestone_id=" + milestone_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", achieved_at=" + achieved_at +
                ", project=" + project +
                '}';
    }

    public static class MilestoneBuilder {
        private String title;
        private String description;
        private Project project;

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

        //Copy method to be used for updating, Allowing to modify only the fields that need to be updated while keeping the rest of the data intact.
        public MilestoneBuilder copy(Milestone milestone) {
            this.title = milestone.title;
            this.description = milestone.description;
            this.project = milestone.project;
            return this;
        }

        public Milestone build() {
            return new Milestone(this);
        }
    }
}
