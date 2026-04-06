package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    //Ai used here to implement a custom query to find projects by their project stage, reviewed the code before implementing,
    @Query("select distinct p from Project p join p.projectStage ps where ps in :projectStage")
    List<Project> findByProjectStage(@Param("projectStage") List<ProjectStage> projectStage);

    List<Project> findByTitle(String title);
    List<Project> findByDeveloperDeveloperId(Integer developerId);

    //Ai used here to implement a custom query to find projects by their project stage, reviewed the code before implementing,
    @Query("select distinct p from Project p join p.projectStage ps where ps = :stage order by p.updatedAt desc")
    List<Project> findByProjectStageContainingOrderByUpdatedAtDesc(@Param("stage") ProjectStage stage);
}
