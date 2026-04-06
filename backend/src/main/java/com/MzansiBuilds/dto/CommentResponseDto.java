package com.MzansiBuilds.dto;

import java.time.LocalDateTime;

public record CommentResponseDto(

        Integer commentId,
        String content,
        Integer developerId,
        Integer projectId,
        LocalDateTime createdAt
) {
}
