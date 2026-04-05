package com.MzansiBuilds.dto;

import com.MzansiBuilds.enums.RequestStatus;
import jakarta.validation.constraints.Size;

public record CollaborationRequestUpdateRequestDto(

        @Size(max = 500, message = "Message must be less than 500 characters")
        String message,

        RequestStatus status
) {
}
