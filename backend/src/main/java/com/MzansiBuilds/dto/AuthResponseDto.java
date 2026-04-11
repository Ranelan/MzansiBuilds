package com.MzansiBuilds.dto;

public record AuthResponseDto(
        String accessToken,
        String tokenType,
        long expiresInMs,
        Integer developerId,
        String username,
        String email
) {
}
