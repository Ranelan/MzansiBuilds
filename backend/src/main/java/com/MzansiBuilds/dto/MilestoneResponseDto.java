package com.MzansiBuilds.dto;

import java.time.LocalDateTime;

public record MilestoneResponseDto(

        Integer milestoneId,
        String title,
        String description,
        Integer projectId,
        LocalDateTime achievedAt
) {
}
