package com.MzansiBuilds.dto;

import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectUpdateRequestDto(

        @Size(max = 255, message = "Project title must be at most 255 characters")
        String title,

        @Size(max = 1000, message = "Project description must be at most 1000 characters")
        String description,

        @Size(max = 255, message = "Tech stack must be at most 255 characters")
        String techStack,

        @Size(max = 255, message = "Repository link must be at most 255 characters")
        String repoLink,

        List<ProjectStage> projectStage,
        List<Support> supportNeeded

) {
}
