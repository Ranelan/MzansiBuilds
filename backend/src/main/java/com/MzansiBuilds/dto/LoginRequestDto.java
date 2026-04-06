package com.MzansiBuilds.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(

        @NotBlank
        String username,

        @NotBlank
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}
