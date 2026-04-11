package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeveloperService developerService;

    private Developer developer;

    @BeforeEach
    void setUp() {
        String unique = UUID.randomUUID().toString().substring(0, 8);
        developer = new Developer.DeveloperBuilder()
                .setUsername("testdeveloper_" + unique)
                .setPassword("password@123")
                .setEmail("testdev_" + unique + "@gmail.com")
                .setBio("Test developer for project service tests")
                .build();
        developer = developerService.create(developer);
        assertTrue(developer.getDeveloperId() > 0, "Developer ID should be generated after creation");
    }

    @Test
    @Order(1)
    void create() {
        Project created = projectService.create(buildProject("Project Create", List.of(ProjectStage.PLANNING), developer));

        assertNotNull(created);
        assertTrue(created.getProjectId() > 0);
        assertEquals("Project Create", created.getTitle());
        assertEquals(developer.getDeveloperId(), created.getDeveloper().getDeveloperId());
    }

    @Test
    @Order(2)
    void read() {
        Project created = projectService.create(buildProject("Project Read", List.of(ProjectStage.IN_PROGRESS), developer));

        Project found = projectService.read(created.getProjectId());

        assertNotNull(found);
        assertEquals(created.getProjectId(), found.getProjectId());
        assertEquals("Project Read", found.getTitle());
    }

    @Test
    @Order(3)
    void update() {
        Project created = projectService.create(buildProject("Project Update", List.of(ProjectStage.PLANNING), developer));

        Project toUpdate = new Project.ProjectBuilder()
                .copy(created)
                .setTitle("Project Update v2")
                .setDescription("Updated description")
                .setTechStack("Spring Boot, MySQL")
                .setRepoLink("https://github.com/example/project-update-v2")
                .setProjectStage(new ArrayList<>(List.of(ProjectStage.TESTING)))
                .setSupportNeeded(new ArrayList<>(List.of(Support.FEEDBACK)))
                .setDeveloper(created.getDeveloper())
                .build();

        Project updated = projectService.update(toUpdate);

        assertNotNull(updated);
        assertEquals(created.getProjectId(), updated.getProjectId());
        assertEquals("Project Update v2", updated.getTitle());
        assertTrue(updated.getProjectStage().contains(ProjectStage.TESTING));
    }

    @Test
    @Order(4)
    void completeProject() {
        Project created = projectService.create(buildProject("Project Complete", List.of(ProjectStage.IN_PROGRESS), developer));

        Project completed = projectService.completeProject(created.getProjectId());

        assertNotNull(completed);
        assertTrue(completed.getProjectStage().contains(ProjectStage.LAUNCHED));
        assertTrue(completed.getProjectStage().contains(ProjectStage.IN_PROGRESS));
    }

    @Test
    @Order(5)
    void findCelebrationWallProjects() {
        Project launchedCandidate = projectService.create(buildProject("Launched Candidate", List.of(ProjectStage.TESTING), developer));
        Project notLaunched = projectService.create(buildProject("Not Launched", List.of(ProjectStage.PLANNING), developer));
        projectService.completeProject(launchedCandidate.getProjectId());

        List<Project> projects = projectService.findCelebrationWallProjects();

        assertTrue(projects.stream().anyMatch(p -> p.getProjectId() == launchedCandidate.getProjectId()));
        assertFalse(projects.stream().anyMatch(p -> p.getProjectId() == notLaunched.getProjectId()));
    }

    @Test
    @Order(6)
    void findAll() {
        Project first = projectService.create(buildProject("All First", List.of(ProjectStage.PLANNING), developer));
        Project second = projectService.create(buildProject("All Second", List.of(ProjectStage.TESTING), developer));

        List<Project> projects = projectService.findAll();

        assertTrue(projects.stream().anyMatch(p -> p.getProjectId() == first.getProjectId()));
        assertTrue(projects.stream().anyMatch(p -> p.getProjectId() == second.getProjectId()));
    }

    @Test
    @Order(7)
    void findByProjectStage() {
        Project testingProject = projectService.create(buildProject("Stage Testing", List.of(ProjectStage.TESTING), developer));
        projectService.create(buildProject("Stage Planning", List.of(ProjectStage.PLANNING), developer));

        List<Project> testingProjects = projectService.findByProjectStage(List.of(ProjectStage.TESTING));

        assertTrue(testingProjects.stream().anyMatch(p -> p.getProjectId() == testingProject.getProjectId()));
        assertTrue(testingProjects.stream().allMatch(p -> p.getProjectStage().contains(ProjectStage.TESTING)));
    }

    @Test
    @Order(8)
    void findByDeveloperId() {
        Project ownProject = projectService.create(buildProject("Own Project", List.of(ProjectStage.IN_PROGRESS), developer));

        String unique = UUID.randomUUID().toString().substring(0, 8);
        Developer anotherDeveloper = developerService.create(new Developer.DeveloperBuilder()
                .setUsername("otherdev_" + unique)
                .setPassword("password@123")
                .setEmail("otherdev_" + unique + "@gmail.com")
                .setBio("Another dev")
                .build());

        Project otherProject = projectService.create(buildProject("Other Project", List.of(ProjectStage.IN_PROGRESS), anotherDeveloper));

        List<Project> ownProjects = projectService.findByDeveloperId(developer.getDeveloperId());

        assertTrue(ownProjects.stream().anyMatch(p -> p.getProjectId() == ownProject.getProjectId()));
        assertFalse(ownProjects.stream().anyMatch(p -> p.getProjectId() == otherProject.getProjectId()));
    }

    @Test
    @Order(9)
    void delete() {
        Project created = projectService.create(buildProject("Delete Project", List.of(ProjectStage.PLANNING), developer));

        projectService.delete(created.getProjectId());

        Project found = projectService.read(created.getProjectId());
        assertNull(found);
    }

    private Project buildProject(String title, List<ProjectStage> stages, Developer owner) {
        return new Project.ProjectBuilder()
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