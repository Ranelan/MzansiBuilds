package com.MzansiBuilds.dto;

import java.time.LocalDateTime;

public record DeveloperResponseDto(

        Integer developerId,
        String username,
        String email,
        String bio,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
