package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;

import java.util.List;

public interface IProjectService extends IService<Project, Integer> {

    Project completeProject(Integer projectId);
    List<Project> findCelebrationWallProjects();
    List<Project> findAll();
    List<Project> findByProjectStage(List<ProjectStage> projectStage);
    List<Project> findByDeveloperId(Integer developerId);
}
