package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.repository.ProjectRepository;
import com.MzansiBuilds.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                    .setUpdatedAt(LocalDateTime.now())
                    .build();
            return projectRepository.save(updateProject);
        }
        return null;
    }

    @Override
    public Project completeProject(Integer projectId) {
        return null;
    }

    @Override
    public List<Project> findCelebrationWallProjects() {
        return List.of();
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByProjectStage(List<String> projectStage) {
        return List.of();
    }

    @Override
    public List<Project> findByTitle(String title) {
        return List.of();
    }

    @Override
    public List<Project> findByDeveloperId(Integer developerId) {
        return List.of();
    }

    @Override
    public void delete(Integer id) {
            projectRepository.deleteById(id);
    }
}

