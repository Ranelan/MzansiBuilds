package com.MzansiBuilds.service.imp;

import com.MzansiBuilds.domain.CollaborationRequest;
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


    @Override
    public CollaborationRequest create(CollaborationRequest collaborationRequest) {
        return collaborationRequestRepository.save(collaborationRequest);
    }

    @Override
    public CollaborationRequest read(Integer integer) {
        return collaborationRequestRepository.findById(integer).orElse(null);
    }

    @Override
    public CollaborationRequest update(CollaborationRequest collaborationRequest) {
        if(collaborationRequest == null || collaborationRequest.getRequestId() == 0){
            return null;
        }
        Optional<CollaborationRequest> existingRequest = collaborationRequestRepository.findById(collaborationRequest.getRequestId());
        if(existingRequest.isPresent()){
            CollaborationRequest updateRequest = new CollaborationRequest.CollaborationRequestBuilder().copy(existingRequest.get())
                    .setStatus(collaborationRequest.getStatus())
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
    public void delete(Integer id) {

    }
}
