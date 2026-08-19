package com.dev_spring.sentin.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeEmailRequest(

    @NotBlank(message = "password is required")
    String password,

    @NotBlank(message = "new_email is required")
    @Email(message = "new_email format is invalid")
    @Size(max = 50, message = "new_email cannot exceed 50 characters")
    String newEmail

) {}