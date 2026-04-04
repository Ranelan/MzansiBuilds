package com.MzansiBuilds.service;

import com.MzansiBuilds.domain.CollaborationRequest;

import java.util.List;

public interface ICollaborationRequestService extends IService<CollaborationRequest, Integer> {

    List<CollaborationRequest> findByProjectId(Integer projectId);
    List<CollaborationRequest> findByDeveloperId(Integer developerId);


}
