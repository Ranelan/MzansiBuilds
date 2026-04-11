package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.CollaborationRequest;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.enums.RequestStatus;
import com.MzansiBuilds.repository.CollaborationRequestRepository;
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
class CollaborationServiceTest {

    @Mock
    private CollaborationRequestRepository collaborationRequestRepository;

    @InjectMocks
    private CollaborationService collaborationService;

    private Developer developer;
    private com.MzansiBuilds.domain.Project project;
    private CollaborationRequest request;

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

        request = new CollaborationRequest.CollaborationRequestBuilder()
                .setRequestId(1)
                .setMessage("Please collaborate with me")
                .setStatus(RequestStatus.PENDING)
                .setDeveloper(developer)
                .setProject(project)
                .build();
    }

    @Test
    void create() {
        when(collaborationRequestRepository.save(any(CollaborationRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CollaborationRequest created = collaborationService.create(new CollaborationRequest.CollaborationRequestBuilder()
                .setMessage("New request")
                .setStatus(RequestStatus.ACCEPTED)
                .setDeveloper(developer)
                .setProject(project)
                .build());

        assertNotNull(created);
        assertEquals("New request", created.getMessage());
        assertEquals(RequestStatus.PENDING, created.getStatus());
        verify(collaborationRequestRepository, times(1)).save(any(CollaborationRequest.class));

        assertNull(collaborationService.create(null));
    }

    @Test
    void read() {
        when(collaborationRequestRepository.findById(1)).thenReturn(Optional.of(request));
        when(collaborationRequestRepository.findById(99)).thenReturn(Optional.empty());

        assertNotNull(collaborationService.read(1));
        assertEquals("Please collaborate with me", collaborationService.read(1).getMessage());
        assertNull(collaborationService.read(99));
    }

    @Test
    void update() {
        CollaborationRequest existing = new CollaborationRequest.CollaborationRequestBuilder()
                .setRequestId(1)
                .setMessage("Old message")
                .setStatus(RequestStatus.PENDING)
                .setDeveloper(developer)
                .setProject(project)
                .build();

        CollaborationRequest updateRequest = new CollaborationRequest.CollaborationRequestBuilder()
                .copy(existing)
                .setMessage("Updated message")
                .setStatus(RequestStatus.ACCEPTED)
                .build();

        when(collaborationRequestRepository.findById(1)).thenReturn(Optional.of(existing));
        when(collaborationRequestRepository.save(any(CollaborationRequest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CollaborationRequest updated = collaborationService.update(updateRequest);

        assertNotNull(updated);
        assertEquals(1, updated.getRequestId());
        assertEquals("Updated message", updated.getMessage());
        assertEquals(RequestStatus.ACCEPTED, updated.getStatus());

        CollaborationRequest keepStatusRequest = new CollaborationRequest.CollaborationRequestBuilder()
                .setRequestId(1)
                .setMessage("Keep status")
                .setDeveloper(developer)
                .setProject(project)
                .build();

        CollaborationRequest keptStatus = collaborationService.update(keepStatusRequest);
        assertNotNull(keptStatus);
        assertEquals(RequestStatus.PENDING, keptStatus.getStatus());

        assertNull(collaborationService.update(null));
        assertNull(collaborationService.update(new CollaborationRequest.CollaborationRequestBuilder().setRequestId(0).build()));

        when(collaborationRequestRepository.findById(2)).thenReturn(Optional.empty());
        assertNull(collaborationService.update(new CollaborationRequest.CollaborationRequestBuilder().setRequestId(2).setMessage("Missing").build()));
    }

    @Test
    void findByProjectId() {
        when(collaborationRequestRepository.findByProjectProjectId(1)).thenReturn(List.of(request));

        List<CollaborationRequest> requests = collaborationService.findByProjectId(1);

        assertEquals(1, requests.size());
        assertEquals("Please collaborate with me", requests.get(0).getMessage());
    }

    @Test
    void findByDeveloperId() {
        when(collaborationRequestRepository.findByDeveloperDeveloperId(1)).thenReturn(List.of(request));

        List<CollaborationRequest> requests = collaborationService.findByDeveloperId(1);

        assertEquals(1, requests.size());
        assertEquals("Please collaborate with me", requests.get(0).getMessage());
    }

    @Test
    void findAll() {
        when(collaborationRequestRepository.findAll()).thenReturn(List.of(request));

        List<CollaborationRequest> requests = collaborationService.findAll();

        assertEquals(1, requests.size());
        assertEquals("Please collaborate with me", requests.get(0).getMessage());
    }

    @Test
    void delete() {
        collaborationService.delete(1);

        verify(collaborationRequestRepository, times(1)).deleteById(1);
    }
}