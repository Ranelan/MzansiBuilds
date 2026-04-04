package com.MzansiBuilds.factory;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.domain.ProjectStage;
import com.MzansiBuilds.domain.Support;
import com.MzansiBuilds.util.Helper;

import java.time.LocalDateTime;
import java.util.List;

public class ProjectFactory {

    public static Project createProject(String title, String description, String techStack, String repoLink, LocalDateTime createdAt, LocalDateTime updatedAt, List<ProjectStage> projectStage, List<Support> supportNeeded, Developer developer){

        if(Helper.isNullOrEmpty(title)
                || Helper.isNullOrEmpty(description)
                || Helper.isNullOrEmpty(techStack)
                || Helper.isNullOrEmpty(repoLink)
                || !Helper.isValidTimeStamp(createdAt)
                || !Helper.isValidTimeStamp(updatedAt)
                || projectStage == null
                || supportNeeded == null
                || developer == null){
            return null;
        }

        return new Project.ProjectBuilder()
                .setTitle(title)
                .setDescription(description)
                .setTechStack(techStack)
                .setRepoLink(repoLink)
                .setUpdatedAt(updatedAt)
                .setProjectStage(projectStage)
                .setSupportNeeded(supportNeeded)
                .setDeveloper(developer)
                .build();
    }
}
