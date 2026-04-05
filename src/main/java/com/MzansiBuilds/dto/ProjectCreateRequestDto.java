package com.MzansiBuilds.dto;

import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ProjectCreateRequestDto(

        @NotBlank(message = "Project title is required")
        String title,

        @NotBlank(message = "Project description is required")
        String description,

        @NotBlank(message = "Tech stack is required")
        String techStack,

        @NotBlank(message = "Repository link is required")
        String repoLink,

        @NotNull(message = "Project stage is required")
        @NotEmpty(message = "Project stage cannot be empty")
        List<ProjectStage> projectStage,

        @NotNull(message = "Support needed is required")
        @NotEmpty(message = "Support needed cannot be empty")
        List<Support> supportNeeded,

        @NotNull(message = "Developer ID is required")
        @Positive(message = "Developer ID must be a positive integer")
        Integer developerId

) {}
