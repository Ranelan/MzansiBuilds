package com.MzansiBuilds.repository;

import com.MzansiBuilds.domain.CollaborationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaborationRequestRepository extends JpaRepository<CollaborationRequest, Integer> {

    List<CollaborationRequest> findByProjectProjectId(Integer projectId);
    List<CollaborationRequest> findByDeveloperDeveloperId(Integer developerId);

}
