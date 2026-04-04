package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.domain.ProjectStage;
import com.MzansiBuilds.domain.Support;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByProjectStage(List<ProjectStage> projectStage);
    List<Project> findByTitle(String title);
    List<Project> findByDeveloperId(Integer developerId);
    List<Project> findBySupportNeeded(List<Support> support);
    List<Project> findByProjectStageContainingOrderByUpdatedAtDesc(ProjectStage stage);
}
