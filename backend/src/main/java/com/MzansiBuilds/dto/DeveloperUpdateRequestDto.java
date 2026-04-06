package com.MzansiBuilds.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DeveloperUpdateRequestDto(

        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @Email(message = "Invalid email format")
        String email,

        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @Size(max = 500, message = "Bio must be at most 500 characters")
        String bio

) {}
