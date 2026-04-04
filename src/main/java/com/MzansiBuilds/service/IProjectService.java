package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.Project;

import java.util.List;

public interface IProjectService extends IService<Project, Integer> {

    Project completeProject(Integer projectId);
    List<Project> findCelebrationWallProjects();
    List<Project> findAll();
    List<Project> findByProjectStage(List<String> projectStage);
    List<Project> findByTitle(String title);
    List<Project> findByDeveloperId(Integer developerId);
}
