package com.MzansiBuilds.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int project_id;
    private String title;
    private String description;
    private String techStack;
    private String repoLink;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    //Enums for project stage and support needed.
    @Enumerated(EnumType.STRING)
    private List<ProjectStage> projectStage;
    @Enumerated(EnumType.STRING)
    private List<Support> supportNeeded;

    //relationship with Developer, Many projects can be created by one developer.
    @ManyToOne
    @JoinColumn(name = "developer_id")
    private Developer developer;

    //Constructor without parameters.
    public Project() {

    }

    //Constructor that takes all fields as parameters.
    public Project(int project_id, String title, String description, String techStack, String repoLink, LocalDateTime created_at, LocalDateTime updated_at, List<ProjectStage> projectStage, List<Support> supportNeeded, Developer developer) {
        this.project_id = project_id;
        this.title = title;
        this.description = description;
        this.techStack = techStack;
        this.repoLink = repoLink;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.projectStage = projectStage;
        this.supportNeeded = supportNeeded;
        this.developer = developer;
    }

    //Constructor that takes a builder object, allowing for flexible and readable object creation.
    public Project(ProjectBuilder projectBuilder) {
        this.title = projectBuilder.title;
        this.description = projectBuilder.description;
        this.techStack = projectBuilder.techStack;
        this.repoLink = projectBuilder.repoLink;
        this.updated_at = projectBuilder.updated_at;
        this.projectStage = projectBuilder.projectStage;
        this.supportNeeded = projectBuilder.supportNeeded;
        this.developer = projectBuilder.developer;
    }

    //Getters for all fields.
    public int getProject_id() {
        return project_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTechStack() {
        return techStack;
    }

    public String getRepoLink() {
        return repoLink;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public List<ProjectStage> getProjectStage() {
        return projectStage;
    }

    public List<Support> getSupportNeeded() {
        return supportNeeded;
    }

    public Developer getDeveloper() {
        return developer;
    }

    //toString method for easy debugging and logging.
    @Override
    public String toString() {
        return "Project{" +
                "project_id=" + project_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", techStack='" + techStack + '\'' +
                ", repoLink='" + repoLink + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", projectStage=" + projectStage +
                ", supportNeeded=" + supportNeeded +
                ", developer=" + developer +
                '}';
    }

    //Builder pattern.
    public static class ProjectBuilder {
        private String title;
        private String description;
        private String techStack;
        private String repoLink;
        private LocalDateTime updated_at;
        private List<ProjectStage> projectStage;
        private List<Support> supportNeeded;
        private Developer developer;

        //Setters for each field, returning the builder instance..
        public ProjectBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public ProjectBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder setTechStack(String techStack) {
            this.techStack = techStack;
            return this;
        }

        public ProjectBuilder setRepoLink(String repoLink) {
            this.repoLink = repoLink;
            return this;
        }


        public ProjectBuilder setUpdated_at(LocalDateTime updated_at) {
            this.updated_at = updated_at;
            return this;
        }

        public ProjectBuilder setProjectStage(List<ProjectStage> projectStage) {
            this.projectStage = projectStage;
            return this;
        }

        public ProjectBuilder setSupportNeeded(List<Support> supportNeeded) {
            this.supportNeeded = supportNeeded;
            return this;
        }

        public ProjectBuilder setDeveloper(Developer developer) {
            this.developer = developer;
            return this;
        }

        //Copy method to be used for updating, Allowing to modify only the fields that need to be updated while keeping the rest of the data intact.
        public ProjectBuilder copy(Project project) {
            this.title = project.title;
            this.description = project.description;
            this.techStack = project.techStack;
            this.repoLink = project.repoLink;
            this.updated_at = project.updated_at;
            this.projectStage = project.projectStage;
            this.supportNeeded = project.supportNeeded;
            this.developer = project.developer;
            return this;
        }

        public Project build() {
            return new Project(this);
        }
    }
}
