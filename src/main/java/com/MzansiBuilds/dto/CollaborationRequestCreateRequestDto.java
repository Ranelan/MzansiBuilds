package com.MzansiBuilds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CollaborationRequestCreateRequestDto(

        @NotBlank(message = "Message is required")
        String message,

        @NotNull(message = "Developer ID is required")
        @Positive(message = "Developer ID must be positive")
        Integer developerId,

        @NotNull(message = "Project ID is required")
        @Positive(message = "Project ID must be positive")
        Integer projectId
) {
}
