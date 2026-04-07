package com.MzansiBuilds.controller;

import com.MzansiBuilds.domain.Comment;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.dto.CommentCreateRequestDto;
import com.MzansiBuilds.dto.CommentResponseDto;
import com.MzansiBuilds.dto.CommentUpdateDto;
import com.MzansiBuilds.service.imp.CommentService;
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
@RequestMapping("/api/comment")
@Validated
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private DeveloperService developerService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentCreateRequestDto request) {
        Developer developer = developerService.read(request.developerId());
        if (developer == null) {
            return ResponseEntity.notFound().build();
        }

        Project project = projectService.read(request.projectId());
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        Comment commentToCreate = new Comment.CommentBuilder()
                .setContent(request.content())
                .setDeveloper(developer)
                .setProject(project)
                .build();

        Comment createdComment = commentService.create(commentToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDto(createdComment));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<CommentResponseDto> getById(@PathVariable @Positive Integer id) {
        Comment comment = commentService.read(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toResponseDto(comment));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentResponseDto>> getAll() {
        List<CommentResponseDto> comments = commentService.findAll().stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<CommentResponseDto>> getByProjectId(@PathVariable @Positive Integer projectId) {
        List<CommentResponseDto> comments = commentService.findByProjectId(projectId).stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/developer/{developerId}")
    public ResponseEntity<List<CommentResponseDto>> getByDeveloperId(@PathVariable @Positive Integer developerId) {
        List<CommentResponseDto> comments = commentService.findByDeveloperId(developerId).stream().map(this::toResponseDto).toList();
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable @Positive Integer id,
                                                            @Valid @RequestBody CommentUpdateDto request) {
        Comment existing = commentService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        Comment commentToUpdate = new Comment.CommentBuilder()
                .copy(existing)
                .setContent(request.content())
                .build();

        Comment updated = commentService.update(commentToUpdate);
        return ResponseEntity.ok(toResponseDto(updated));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable @Positive Integer id) {
        Comment existing = commentService.read(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //AI used to generate this method, it converts a CollaborationRequest entity to a CollaborationRequestResponseDto
    private CommentResponseDto toResponseDto(Comment comment) {
        Integer developerId = comment.getDeveloper() != null ? comment.getDeveloper().getDeveloperId() : null;
        Integer projectId = comment.getProject() != null ? comment.getProject().getProjectId() : null;
        return new CommentResponseDto(
                comment.getCommentId(),
                comment.getContent(),
                developerId,
                projectId,
                comment.getCreatedAt()
        );
    }
}
