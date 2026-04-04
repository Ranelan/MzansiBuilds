package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.CollaborationRequest;
import com.MzansiBuilds.domain.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Integer> {

    List<CollaborationRequest> findRequestByProjectId(Integer projectId);
    List<CollaborationRequest> findRequestByDeveloperId(Integer developerId);

}
