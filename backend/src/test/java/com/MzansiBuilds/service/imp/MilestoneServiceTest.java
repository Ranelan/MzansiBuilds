package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Milestone;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;
import com.MzansiBuilds.repository.MilestoneRepository;
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
class MilestoneServiceTest {

    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private MilestoneService milestoneService;

    private Project project;
    private Milestone milestone;

    @BeforeEach
    void setUp() {
        Developer developer = new Developer.DeveloperBuilder()
                .setDeveloperId(1)
                .setUsername("dev1")
                .setEmail("dev1@gmail.com")
                .setPassword("password@123")
                .setBio("developer")
                .build();

        project = new Project.ProjectBuilder()
                .setProjectId(1)
                .setTitle("Project One")
                .setDescription("project")
                .setTechStack("Java")
                .setRepoLink("https://github.com/example/project-one")
                .setProjectStage(new ArrayList<>(List.of(ProjectStage.PLANNING)))
                .setSupportNeeded(new ArrayList<>(List.of(Support.COLLABORATOR)))
                .setDeveloper(developer)
                .build();

        milestone = new Milestone.MilestoneBuilder()
                .setMilestoneId(1)
                .setTitle("Milestone 1")
                .setDescription("Milestone description")
                .setProject(project)
                .build();
    }
//Ai Used to create some of the repetitive tasks to fast track the development of the test cases,
// I reviewed the code before implementing to ensure it fits the requirements of the test cases.
    @Test
    void create() {
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Milestone created = milestoneService.create(new Milestone.MilestoneBuilder()
                .setTitle("Milestone Create")
                .setDescription("Create description")
                .setProject(project)
                .build());

        assertNotNull(created);
        assertEquals("Milestone Create", created.getTitle());
        assertEquals(project.getProjectId(), created.getProject().getProjectId());
        verify(milestoneRepository, times(1)).save(any(Milestone.class));
    }

    @Test
    void read() {
        when(milestoneRepository.findById(1)).thenReturn(Optional.of(milestone));
        when(milestoneRepository.findById(99)).thenReturn(Optional.empty());

        assertNotNull(milestoneService.read(1));
        assertEquals("Milestone 1", milestoneService.read(1).getTitle());
        assertNull(milestoneService.read(99));
    }

    @Test
    void update() {
        Milestone existing = new Milestone.MilestoneBuilder()
                .setMilestoneId(1)
                .setTitle("Old Title")
                .setDescription("Old description")
                .setProject(project)
                .build();

        Milestone updateRequest = new Milestone.MilestoneBuilder()
                .copy(existing)
                .setTitle("Updated Title")
                .setDescription("Updated description")
                .build();

        when(milestoneRepository.findById(1)).thenReturn(Optional.of(existing));
        when(milestoneRepository.save(any(Milestone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Milestone updated = milestoneService.update(updateRequest);

        assertNotNull(updated);
        assertEquals(1, updated.getMilestoneId());
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated description", updated.getDescription());

        assertNull(milestoneService.update(null));
        assertNull(milestoneService.update(new Milestone.MilestoneBuilder().setMilestoneId(0).build()));

        when(milestoneRepository.findById(2)).thenReturn(Optional.empty());
        assertNull(milestoneService.update(new Milestone.MilestoneBuilder().setMilestoneId(2).setTitle("Missing").build()));
    }

    @Test
    void findByProjectId() {
        when(milestoneRepository.findByProjectProjectId(1)).thenReturn(List.of(milestone));

        List<Milestone> milestones = milestoneService.findByProjectId(1);

        assertEquals(1, milestones.size());
        assertEquals("Milestone 1", milestones.get(0).getTitle());
    }

    @Test
    void findAll() {
        when(milestoneRepository.findAll()).thenReturn(List.of(milestone));

        List<Milestone> milestones = milestoneService.findAll();

        assertEquals(1, milestones.size());
        assertEquals("Milestone 1", milestones.get(0).getTitle());
    }

    @Test
    void delete() {
        milestoneService.delete(1);

        verify(milestoneRepository, times(1)).deleteById(1);
    }
}