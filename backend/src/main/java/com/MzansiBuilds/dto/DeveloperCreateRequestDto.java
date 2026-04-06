package com.MzansiBuilds.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DeveloperCreateRequestDto(

        @NotBlank(message = "username is required")
        @Size(min = 3, max = 30, message = "username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "password is required")
        @Size(min = 6, message = "password must be at least 6 characters long")
        String password,

        @Size(max = 500, message = "bio must be less than 500 characters")
        String bio
) {}
