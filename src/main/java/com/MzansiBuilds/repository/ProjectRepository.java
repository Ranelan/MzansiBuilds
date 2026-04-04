package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.domain.ProjectStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByProjectStage(List<ProjectStage> projectStage);
    List<Project> findByTitle(String title);
    List<Project> findByDeveloper_id(Integer developer_id);
}
