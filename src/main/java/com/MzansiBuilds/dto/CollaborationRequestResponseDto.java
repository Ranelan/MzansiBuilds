package com.MzansiBuilds.dto;

import com.MzansiBuilds.enums.RequestStatus;

import java.time.LocalDateTime;

public record CollaborationRequestResponseDto(

        Integer requestId,
        String message,
        RequestStatus status,
        Integer developerId,
        Integer projectId,
        LocalDateTime createdAt

) {

}
