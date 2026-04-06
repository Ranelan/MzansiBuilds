package com.MzansiBuilds.dto;

import jakarta.validation.constraints.Size;

public record MilestoneUpdateRequestDto(

        @Size(max = 120, message = "Title must be at most 120 characters")
        String title,

        @Size(max = 1000, message = "Description must be at most 1000 characters")
        String description

) {
}
