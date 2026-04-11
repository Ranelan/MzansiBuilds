package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Comment;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Developer developer;
    private com.MzansiBuilds.domain.Project project;
    private Comment comment;

    //Ai Used to create some of the repetitive tasks to fast track the development of the test cases,
    //I reviewed the code before implementing to ensure it fits the requirements of the test cases.
    @BeforeEach
    void setUp() {
        developer = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("password@123")
                .setBio("developer")
                .build();

        project = new com.MzansiBuilds.domain.Project.ProjectBuilder()
                .setProjectId(1)
                .setTitle("Project One")
                .setDescription("project")
                .setTechStack("Java")
                .setRepoLink("https://github.com/example/project-one")
                .setProjectStage(new ArrayList<>(List.of(com.MzansiBuilds.enums.ProjectStage.PLANNING)))
                .setSupportNeeded(new ArrayList<>(List.of(com.MzansiBuilds.enums.Support.COLLABORATOR)))
                .setDeveloper(developer)
                .build();

        comment = new Comment.CommentBuilder()
                .setCommentId(1)
                .setContent("Great work")
                .setDeveloper(developer)
                .setProject(project)
                .build();
    }

    @Test
    void create() {
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment created = commentService.create(new Comment.CommentBuilder()
                .setContent("Create comment")
                .setDeveloper(developer)
                .setProject(project)
                .build());

        assertNotNull(created);
        assertEquals("Create comment", created.getContent());
        assertEquals(project.getProjectId(), created.getProject().getProjectId());
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    void read() {
        when(commentRepository.findById(1)).thenReturn(Optional.of(comment));
        when(commentRepository.findById(99)).thenReturn(Optional.empty());

        assertNotNull(commentService.read(1));
        assertEquals("Great work", commentService.read(1).getContent());
        assertNull(commentService.read(99));
    }

    @Test
    void update() {
        Comment existing = new Comment.CommentBuilder()
                .setCommentId(1)
                .setContent("Old content")
                .setDeveloper(developer)
                .setProject(project)
                .build();

        Comment updateRequest = new Comment.CommentBuilder()
                .copy(existing)
                .setContent("Updated content")
                .build();

        when(commentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Comment updated = commentService.update(updateRequest);

        assertNotNull(updated);
        assertEquals(1, updated.getCommentId());
        assertEquals("Updated content", updated.getContent());

        assertNull(commentService.update(null));
        assertNull(commentService.update(new Comment.CommentBuilder().setCommentId(0).build()));

        when(commentRepository.findById(2)).thenReturn(Optional.empty());
        assertNull(commentService.update(new Comment.CommentBuilder().setCommentId(2).setContent("Missing").build()));
    }

    @Test
    void findByProjectId() {
        when(commentRepository.findByProjectProjectId(1)).thenReturn(List.of(comment));

        List<Comment> comments = commentService.findByProjectId(1);

        assertEquals(1, comments.size());
        assertEquals("Great work", comments.get(0).getContent());
    }

    @Test
    void findByDeveloperId() {
        when(commentRepository.findByDeveloperDeveloperId(1)).thenReturn(List.of(comment));

        List<Comment> comments = commentService.findByDeveloperId(1);

        assertEquals(1, comments.size());
        assertEquals("Great work", comments.get(0).getContent());
    }

    @Test
    void findAll() {
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        List<Comment> comments = commentService.findAll();

        assertEquals(1, comments.size());
        assertEquals("Great work", comments.get(0).getContent());
    }

    @Test
    void delete() {
        commentService.delete(1);

        verify(commentRepository, times(1)).deleteById(1);
    }
}