package com.MzansiBuilds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CommentCreateRequestDto(
        @NotBlank(message = "Content is required")
        @Size(max = 1000, message = "Content must be at most 1000 characters")
        String content,

        @NotNull(message = "Developer ID is required")
        @Positive(message = "Developer ID must be positive")
        Integer developerId,

        @NotNull(message = "Project ID is required")
        @Positive(message = "Project ID must be positive")
        Integer projectId
) {
}
