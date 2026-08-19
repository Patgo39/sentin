package com.dev_spring.sentin.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangePasswordRequest(

    @NotBlank(message = "old_password is required")
    String oldPassword,

    @NotBlank(message = "new_password is required")
    @Size(min = 8, max = 255, message = "new_password must be between 8 and 255 characters")
    String newPassword

) {}