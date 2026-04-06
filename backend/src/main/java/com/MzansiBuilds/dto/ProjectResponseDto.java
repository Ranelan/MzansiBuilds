package com.MzansiBuilds.dto;

import com.MzansiBuilds.enums.ProjectStage;
import com.MzansiBuilds.enums.Support;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectResponseDto(
        Integer projectId,
        String title,
        String description,
        String techStack,
        String repoLink,
        List<ProjectStage> projectStage,
        List<Support> supportNeeded,
        Integer developerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
