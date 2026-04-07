package com.MzansiBuilds.controller;

import com.MzansiBuilds.domain.Milestone;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.dto.MilestoneCreateRequestDto;
import com.MzansiBuilds.dto.MilestoneResponseDto;
import com.MzansiBuilds.dto.MilestoneUpdateRequestDto;
import com.MzansiBuilds.service.imp.MilestoneService;
import com.MzansiBuilds.service.imp.ProjectService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/milestone")
@Validated
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<MilestoneResponseDto> createMilestone(@Valid @RequestBody MilestoneCreateRequestDto request) {
        Project project = projectService.read(request.projectId());
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        Milestone milestoneToCreate = new Milestone.MilestoneBuilder()
                .setTitle(request.title())
                .setDescription(request.description())
                .setProject(project)
                .build();

        Milestone createdMilestone = milestoneService.create(milestoneToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(createdMilestone));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MilestoneResponseDto> getById(@PathVariable @Positive Integer id) {
        Milestone milestone = milestoneService.read(id);
        if (milestone == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDto(milestone));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MilestoneResponseDto>> getAll() {
        List<MilestoneResponseDto> milestones = milestoneService.findAll().stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(milestones);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<MilestoneResponseDto>> getByProjectId(@PathVariable @Positive Integer projectId) {
        List<MilestoneResponseDto> milestones = milestoneService.findByProjectId(projectId).stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(milestones);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MilestoneResponseDto> updateMilestone(@PathVariable @Positive Integer id,
                                                                 @Valid @RequestBody MilestoneUpdateRequestDto request) {
        Milestone existing = milestoneService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Milestone milestoneToUpdate = new Milestone.MilestoneBuilder()
                .copy(existing)
                .setTitle(request.title() != null ? request.title() : existing.getTitle())
                .setDescription(request.description() != null ? request.description() : existing.getDescription())
                .build();

        Milestone updated = milestoneService.update(milestoneToUpdate);
        return ResponseEntity.ok(toResponseDto(updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMilestone(@PathVariable @Positive Integer id) {
        Milestone existing = milestoneService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        milestoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //AI used to generate this method, it converts a CollaborationRequest entity to a CollaborationRequestResponseDto
    private MilestoneResponseDto toResponseDto(Milestone milestone) {
        Integer projectId = milestone.getProject() != null ? milestone.getProject().getProjectId() : null;
        return new MilestoneResponseDto(
                milestone.getMilestoneId(),
                milestone.getTitle(),
                milestone.getDescription(),
                projectId,
                milestone.getAchievedAt()
        );
    }
}
