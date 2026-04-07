package com.MzansiBuilds.controller;

import com.MzansiBuilds.domain.CollaborationRequest;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.dto.CollaborationRequestCreateRequestDto;
import com.MzansiBuilds.dto.CollaborationRequestResponseDto;
import com.MzansiBuilds.dto.CollaborationRequestUpdateRequestDto;
import com.MzansiBuilds.service.ICollaborationRequestService;
import com.MzansiBuilds.service.imp.DeveloperService;
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
@RequestMapping("/api/collaboration-request")
@Validated
public class CollaborationRequestController {

    @Autowired
    private ICollaborationRequestService collaborationRequestService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<CollaborationRequestResponseDto> createRequest(@Valid @RequestBody CollaborationRequestCreateRequestDto request) {
        Developer developer = developerService.read(request.developerId());
        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        Project project = projectService.read(request.projectId());
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        CollaborationRequest requestToCreate = new CollaborationRequest.CollaborationRequestBuilder()
                .setMessage(request.message())
                .setDeveloper(developer)
                .setProject(project)
                .build();

        CollaborationRequest createdRequest = collaborationRequestService.create(requestToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(createdRequest));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CollaborationRequestResponseDto> getById(@PathVariable @Positive Integer id) {
        CollaborationRequest collaborationRequest = collaborationRequestService.read(id);
        if (collaborationRequest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDto(collaborationRequest));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CollaborationRequestResponseDto>> getAll() {
        List<CollaborationRequestResponseDto> requests = collaborationRequestService.findAll().stream()
                .map(this::toResponseDto)
                .toList();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<CollaborationRequestResponseDto>> getByProjectId(@PathVariable @Positive Integer projectId) {
        List<CollaborationRequestResponseDto> requests = collaborationRequestService.findByProjectId(projectId).stream()
                .map(this::toResponseDto)
                .toList();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<CollaborationRequestResponseDto>> getByDeveloperId(@PathVariable @Positive Integer developerId) {
        List<CollaborationRequestResponseDto> requests = collaborationRequestService.findByDeveloperId(developerId).stream()
                .map(this::toResponseDto)
                .toList();
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CollaborationRequestResponseDto> updateRequest(@PathVariable @Positive Integer id,
                                                                         @Valid @RequestBody CollaborationRequestUpdateRequestDto request) {
        CollaborationRequest existing = collaborationRequestService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        CollaborationRequest requestToUpdate = new CollaborationRequest.CollaborationRequestBuilder()
                .copy(existing)
                .setMessage(request.message() != null ? request.message() : existing.getMessage())
                .setStatus(request.status() != null ? request.status() : existing.getStatus())
                .build();

        CollaborationRequest updated = collaborationRequestService.update(requestToUpdate);
        return ResponseEntity.ok(toResponseDto(updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable @Positive Integer id) {
        CollaborationRequest existing = collaborationRequestService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        collaborationRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //AI used to generate this method, it converts a CollaborationRequest entity to a CollaborationRequestResponseDto
    private CollaborationRequestResponseDto toResponseDto(CollaborationRequest collaborationRequest) {
        Integer developerId = collaborationRequest.getDeveloper() != null ? collaborationRequest.getDeveloper().getDeveloperId() : null;
        Integer projectId = collaborationRequest.getProject() != null ? collaborationRequest.getProject().getProjectId() : null;

        return new CollaborationRequestResponseDto(
                collaborationRequest.getRequestId(),
                collaborationRequest.getMessage(),
                collaborationRequest.getStatus(),
                developerId,
                projectId,
                collaborationRequest.getCreatedAt()
        );
    }
}
