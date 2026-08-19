package com.dev_spring.sentin.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

    @NotBlank(message = "given_name is required")
    @Size(max = 20, message = "given_name cannot exceed 20 characters")
    String givenName,

    @Size(max = 20, message = "middle_name cannot exceed 20 characters")
    String middleName,

    @NotBlank(message = "family_name is required")
    @Size(max = 60, message = "family_name cannot exceed 60 characters")
    String familyName,

    @NotBlank(message = "username is required")
    @Size(max = 15, message = "username cannot exceed 15 characters")
    String username,

    @NotBlank(message = "password is required")
    @Size(min = 8, max = 255, message = "password must be between 8 and 255 characters")
    String password,

    @NotBlank(message = "email is required")
    @Email(message = "email format is invalid")
    @Size(max = 50, message = "email cannot exceed 50 characters")
    String email,

    @NotNull(message = "birth_date is required")
    @PastOrPresent(message = "birth_date cannot be a future date")
    LocalDate birthDate,

    @NotBlank(message = "postal_code is required")
    @Pattern(regexp = "^[0-9]{5}$", message = "postal_code must have 5 digits")
    String postalCode,

    @NotBlank(message = "phone_number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "phone_number must have 10 digits")
    String phoneNumber,

    @NotBlank(message = "rfc is required")
    @Size(min = 13, max = 13, message = "rfc must have 13 characters")
    String rfc

) {}