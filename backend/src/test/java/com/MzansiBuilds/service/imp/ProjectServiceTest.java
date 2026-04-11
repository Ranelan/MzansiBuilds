package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;
import com.MzansiBuilds.repository.ProjectRepository;
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
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Developer developer;
    private Project project;

    @BeforeEach
    void setUp() {
        developer = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("password@123")
                .setBio("Test developer for project service tests")
                .build();

        project = buildProject(1, "Project One", List.of(ProjectStage.PLANNING), developer);
    }

    @Test
    void create() {
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project created = projectService.create(buildProject(0, "Project Create", List.of(ProjectStage.PLANNING), developer));

        assertNotNull(created);
        assertEquals("Project Create", created.getTitle());
        assertEquals("Description for Project Create", created.getDescription());
        assertEquals(developer.getDeveloperId(), created.getDeveloper().getDeveloperId());
        verify(projectRepository, times(1)).save(any(Project.class));

        assertNull(projectService.create(null));
    }

    @Test
    void read() {
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        assertNotNull(projectService.read(1));
        assertEquals("Project One", projectService.read(1).getTitle());
        assertNull(projectService.read(99));
    }

    @Test
    void update() {
        Project existing = buildProject(1, "Project Update", List.of(ProjectStage.PLANNING), developer);
        Project updateRequest = new Project.ProjectBuilder()
                .copy(existing)
                .setTitle("Project Update v2")
                .setDescription("Updated description")
                .setTechStack("Spring Boot, MySQL")
                .setRepoLink("https://github.com/example/project-update-v2")
                .setProjectStage(new ArrayList<>(List.of(ProjectStage.TESTING)))
                .setSupportNeeded(new ArrayList<>(List.of(Support.FEEDBACK)))
                .build();

        when(projectRepository.findById(1)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project updated = projectService.update(updateRequest);

        assertNotNull(updated);
        assertEquals(1, updated.getProjectId());
        assertEquals("Project Update v2", updated.getTitle());
        assertTrue(updated.getProjectStage().contains(ProjectStage.TESTING));

        assertNull(projectService.update(null));
        assertNull(projectService.update(new Project.ProjectBuilder().setProjectId(0).build()));

        when(projectRepository.findById(2)).thenReturn(Optional.empty());
        assertNull(projectService.update(buildProject(2, "Missing", List.of(ProjectStage.PLANNING), developer)));
    }

    @Test
    void completeProject() {
        Project existing = buildProject(1, "Project Complete", List.of(ProjectStage.IN_PROGRESS), developer);

        when(projectRepository.findById(1)).thenReturn(Optional.of(existing));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project completed = projectService.completeProject(1);

        assertNotNull(completed);
        assertTrue(completed.getProjectStage().contains(ProjectStage.LAUNCHED));
        assertTrue(completed.getProjectStage().contains(ProjectStage.IN_PROGRESS));

        assertNull(projectService.completeProject(null));
        when(projectRepository.findById(99)).thenReturn(Optional.empty());
        assertNull(projectService.completeProject(99));
    }

    @Test
    void findCelebrationWallProjects() {
        Project launched = buildProject(1, "Launched Project", List.of(ProjectStage.LAUNCHED), developer);
        when(projectRepository.findByStageOrderByUpdatedAtDesc(ProjectStage.LAUNCHED)).thenReturn(List.of(launched));

        List<Project> projects = projectService.findCelebrationWallProjects();

        assertEquals(1, projects.size());
        assertEquals("Launched Project", projects.get(0).getTitle());
    }

    @Test
    void findAll() {
        Project first = buildProject(1, "All First", List.of(ProjectStage.PLANNING), developer);
        Project second = buildProject(2, "All Second", List.of(ProjectStage.TESTING), developer);

        when(projectRepository.findAll()).thenReturn(List.of(first, second));

        List<Project> projects = projectService.findAll();

        assertEquals(2, projects.size());
        assertEquals("All First", projects.get(0).getTitle());
        assertEquals("All Second", projects.get(1).getTitle());
    }

    @Test
    void findByProjectStage() {
        Project testingProject = buildProject(1, "Stage Testing", List.of(ProjectStage.TESTING), developer);
        when(projectRepository.findByProjectStage(List.of(ProjectStage.TESTING))).thenReturn(List.of(testingProject));

        List<Project> testingProjects = projectService.findByProjectStage(List.of(ProjectStage.TESTING));

        assertEquals(1, testingProjects.size());
        assertEquals("Stage Testing", testingProjects.get(0).getTitle());
        assertTrue(testingProjects.get(0).getProjectStage().contains(ProjectStage.TESTING));
    }

    @Test
    void findByDeveloperId() {
        Project ownProject = buildProject(1, "Own Project", List.of(ProjectStage.IN_PROGRESS), developer);

        when(projectRepository.findByDeveloperDeveloperId(1)).thenReturn(List.of(ownProject));

        List<Project> ownProjects = projectService.findByDeveloperId(1);

        assertEquals(1, ownProjects.size());
        assertEquals("Own Project", ownProjects.get(0).getTitle());
    }

    @Test
    void delete() {
        projectService.delete(1);

        verify(projectRepository, times(1)).deleteById(1);
    }

    private Project buildProject(int projectId, String title, List<ProjectStage> stages, Developer owner) {
        return new Project.ProjectBuilder()
                .setProjectId(projectId)
                .setTitle(title)
                .setDescription("Description for " + title)
                .setTechStack("Java, Spring Boot")
                .setRepoLink("https://github.com/example/" + title.toLowerCase().replace(" ", "-"))
                .setProjectStage(new ArrayList<>(stages))
                .setSupportNeeded(new ArrayList<>(List.of(Support.COLLABORATOR)))
                .setDeveloper(owner)
                .build();
    }
}