package com.dev_spring.sentin.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(

    @NotBlank(message = "Given name is required")
    @Size(max = 20, message = "Given name must not exceed 20 characters")
    String givenName,

    @Size(max = 20, message = "Middle name must not exceed 20 characters")
    String middleName,

    @NotBlank(message = "Family name is required")
    @Size(max = 60, message = "Family name must not exceed 60 characters")
    String familyName,

    @NotBlank(message = "Username is required")
    @Size(max = 15, message = "username must not exceed 15 characters")
    String username,

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    LocalDate birthDate,

    @NotBlank(message = "Postal code is required")
    @Size(min = 5, max = 5, message = "Postal code must be exactly 5 characters")
    @Pattern(regexp = "^[0-9]{5}$", message = "Postal code must contain numeric characters only")
    String postalCode,

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must contain numeric characters only")
    String phoneNumber,

    @NotBlank(message = "RFC is required")
    @Size(min = 13, max = 13, message = "RFC must be exactly 13 characters")
    @Pattern(regexp = "^[A-Z&Ñ]{4}[0-9]{6}[A-Z0-9]{3}$", message = "Invalid RFC format")
    String rfc

) {}