package com.MzansiBuilds.controller;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.dto.ProjectCreateRequestDto;
import com.MzansiBuilds.dto.ProjectResponseDto;
import com.MzansiBuilds.dto.ProjectUpdateRequestDto;
import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.service.imp.DeveloperService;
import com.MzansiBuilds.service.imp.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeveloperService developerService;

    @PostMapping("/create")
    public ResponseEntity<ProjectResponseDto> createProject(@Valid @RequestBody ProjectCreateRequestDto request) {
        Developer developer = developerService.read(request.developerId());
        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        Project projectToCreate = new Project.ProjectBuilder()
                .setTitle(request.title())
                .setDescription(request.description())
                .setTechStack(request.techStack())
                .setRepoLink(request.repoLink())
                .setProjectStage(request.projectStage())
                .setSupportNeeded(request.supportNeeded())
                .setDeveloper(developer)
                .build();

        Project createdProject = projectService.create(projectToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(createdProject));
    }

    @GetMapping("getById/{id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable @Positive Integer id) {
        Project project = projectService.read(id);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDto(project));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
        List<ProjectResponseDto> projects = projectService.findAll().stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(projects);
    }


    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<ProjectResponseDto>> getByDeveloperId(@PathVariable @Positive Integer developerId) {
        List<ProjectResponseDto> projects = projectService.findByDeveloperId(developerId).stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/stage")
    public ResponseEntity<List<ProjectResponseDto>> getByStage(@RequestParam(required = false) List<ProjectStage> stage) {
        if (stage == null || stage.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<ProjectResponseDto> projects = projectService.findByProjectStage(stage).stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/celebration-wall")
    public ResponseEntity<List<ProjectResponseDto>> getCelebrationWallProjects() {
        List<ProjectResponseDto> projects = projectService.findCelebrationWallProjects().stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectResponseDto> updateProject(@PathVariable @Positive Integer id,
                                                            @Valid @RequestBody ProjectUpdateRequestDto request) {
        Project existing = projectService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Project projectToUpdate = new Project.ProjectBuilder()
                .copy(existing)
                .setTitle(request.title() != null ? request.title() : existing.getTitle())
                .setDescription(request.description() != null ? request.description() : existing.getDescription())
                .setTechStack(request.techStack() != null ? request.techStack() : existing.getTechStack())
                .setRepoLink(request.repoLink() != null ? request.repoLink() : existing.getRepoLink())
                .setProjectStage(request.projectStage() != null ? request.projectStage() : existing.getProjectStage())
                .setSupportNeeded(request.supportNeeded() != null ? request.supportNeeded() : existing.getSupportNeeded())
                .setDeveloper(existing.getDeveloper())
                .build();

        Project updatedProject = projectService.update(projectToUpdate);
        return ResponseEntity.ok(toResponseDto(updatedProject));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ProjectResponseDto> completeProject(@PathVariable @Positive Integer id) {
        Project completedProject = projectService.completeProject(id);
        if (completedProject == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDto(completedProject));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable @Positive Integer id) {
        Project existing = projectService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //AI used to generate this method, it converts a CollaborationRequest entity to a CollaborationRequestResponseDto
    private ProjectResponseDto toResponseDto(Project project) {
        Integer developerId = project.getDeveloper() != null ? project.getDeveloper().getDeveloperId() : null;
        return new ProjectResponseDto(
                project.getProjectId(),
                project.getTitle(),
                project.getDescription(),
                project.getTechStack(),
                project.getRepoLink(),
                project.getProjectStage(),
                project.getSupportNeeded(),
                developerId,
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }
}
