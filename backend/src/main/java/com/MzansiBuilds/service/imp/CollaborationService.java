package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.CollaborationRequest;
import com.MzansiBuilds.enums.RequestStatus;
import com.MzansiBuilds.repository.CollaborationRequestRepository;
import com.MzansiBuilds.service.ICollaborationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollaborationService implements ICollaborationRequestService {

    @Autowired
    private CollaborationRequestRepository collaborationRequestRepository;

// Ai used and reviewed by me,
// I have added null checks and validation to ensure that the create and update methods handle null inputs gracefully.
// In the create method, if the input collaborationRequest is null, it returns null instead of attempting to create a new request.
// In the update method, it checks if the collaborationRequest is null or if its requestId is 0 (indicating it's not a valid request) before proceeding with the update logic.
// If either condition is true, it returns null, preventing any further processing on invalid data. This helps to maintain data integrity and prevents potential errors in the application.
    @Override
    public CollaborationRequest create(CollaborationRequest collaborationRequest) {
        if (collaborationRequest == null) {
            return null;
        }

        CollaborationRequest newRequest = new CollaborationRequest.CollaborationRequestBuilder()
                .setMessage(collaborationRequest.getMessage())
                .setDeveloper(collaborationRequest.getDeveloper())
                .setProject(collaborationRequest.getProject())
                .setStatus(RequestStatus.PENDING)
                .build();

        return collaborationRequestRepository.save(newRequest);
    }

    @Override
    public CollaborationRequest read(Integer integer) {
        return collaborationRequestRepository.findById(integer).orElse(null);
    }

    //AI used and reviewed by me,
    @Override
    public CollaborationRequest update(CollaborationRequest collaborationRequest) {
        if(collaborationRequest == null || collaborationRequest.getRequestId() == 0){
            return null;
        }
        Optional<CollaborationRequest> existingRequest = collaborationRequestRepository.findById(collaborationRequest.getRequestId());
        if(existingRequest.isPresent()){
            RequestStatus statusToUse = collaborationRequest.getStatus() == null
                    ? existingRequest.get().getStatus()
                    : collaborationRequest.getStatus();

            CollaborationRequest updateRequest = new CollaborationRequest.CollaborationRequestBuilder().copy(existingRequest.get())
                    .setMessage(collaborationRequest.getMessage())
                    .setStatus(statusToUse)
                    .build();
            return collaborationRequestRepository.save(updateRequest);
        }
        return null;
    }

    @Override
    public List<CollaborationRequest> findByProjectId(Integer projectId) {
        return collaborationRequestRepository.findByProjectProjectId(projectId);
    }

    @Override
    public List<CollaborationRequest> findByDeveloperId(Integer developerId) {
        return collaborationRequestRepository.findByDeveloperDeveloperId(developerId);
    }

    @Override
    public List<CollaborationRequest> findAll() {
        return collaborationRequestRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        collaborationRequestRepository.deleteById(id);
    }
}
