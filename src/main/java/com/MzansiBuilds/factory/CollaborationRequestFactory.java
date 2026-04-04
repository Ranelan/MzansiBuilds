package com.MzansiBuilds.factory;

import com.MzansiBuilds.domain.CollaborationRequest;
import com.MzansiBuilds.domain.Developer;
import com.MzansiBuilds.domain.Project;
import com.MzansiBuilds.domain.RequestStatus;
import com.MzansiBuilds.util.Helper;

import java.time.LocalDateTime;

public class CollaborationRequestFactory {

    public static CollaborationRequest createCollaborationRequest(String message, LocalDateTime createdAt, RequestStatus status, Developer developer, Project project){

        if (Helper.isNullOrEmpty(message)
                || !Helper.isValidTimeStamp(createdAt)
                || status == null
                || developer == null
                || project == null
        ){
            return null;
        }

        return new CollaborationRequest.CollaborationRequestBuilder()
                .setMessage(message)
                .setStatus(status)
                .setDeveloper(developer)
                .setProject(project)
                .build();
    }
}
