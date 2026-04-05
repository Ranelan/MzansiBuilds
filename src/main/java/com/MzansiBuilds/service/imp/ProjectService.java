package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.repository.ProjectRepository;
import com.MzansiBuilds.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project read(Integer integer) {
        return projectRepository.findById(integer).orElse(null);
    }

    @Override
    public Project update(Project project) {
        if (project == null || project.getProjectId() == 0) {
            return null;
        }
        Optional<Project> existingProject = projectRepository.findById(project.getProjectId());
        if (existingProject.isPresent()) {
            Project updateProject = new Project.ProjectBuilder().copy(existingProject.get())
                    .setTitle(project.getTitle())
                    .setDescription(project.getDescription())
                    .setTechStack(project.getTechStack())
                    .setRepoLink(project.getRepoLink())
                    .setProjectStage(project.getProjectStage())
                    .setSupportNeeded(project.getSupportNeeded())
                    .build();
            return projectRepository.save(updateProject);
        }
        return null;
    }


    //AI Suggestion used here, reviewed the code before implementing,
    // the method checks if the project exists, if it does it adds the LAUNCHED stage to the project stages.
    @Override
    public Project completeProject(Integer projectId) {
        if (projectId == null) {
            return null;
        }

        Optional<Project> existingProject = projectRepository.findById(projectId);
        if (existingProject.isEmpty()) {
            return null;
        }

        Project current = existingProject.get();
        List<ProjectStage> stages = current.getProjectStage() == null
                ? new ArrayList<>()
                : new ArrayList<>(current.getProjectStage());

        if (!stages.contains(ProjectStage.LAUNCHED)) {
            stages.add(ProjectStage.LAUNCHED);
        }

        Project completedProject = new Project(
                current.getProjectId(),
                current.getTitle(),
                current.getDescription(),
                current.getTechStack(),
                current.getRepoLink(),
                current.getCreatedAt(),
                LocalDateTime.now(),
                stages,
                current.getSupportNeeded(),
                current.getDeveloper()
        );

        return projectRepository.save(completedProject);
    }

    @Override
    public List<Project> findCelebrationWallProjects() {
        return projectRepository.findByProjectStageContainingOrderByUpdatedAtDesc(ProjectStage.LAUNCHED);
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByProjectStage(List<ProjectStage> projectStage) {
        return projectRepository.findByProjectStage(projectStage);
    }

    @Override
    public List<Project> findByTitle(String title) {
        return projectRepository.findByTitle(title);
    }

    @Override
    public List<Project> findByDeveloperId(Integer developerId) {
        return projectRepository.findByDeveloperDeveloperId(developerId);
    }

    @Override
    public void delete(Integer id) {
            projectRepository.deleteById(id);
    }
}
