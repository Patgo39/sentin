package com.dev_spring.sentin.dtos;

import java.time.LocalDate;

public record UserResponse(
    Long idUser,
    String givenName,
    String middleName,
    String familyName,
    String username,
    String email,
    LocalDate birthDate,
    String postalCode,
    String phoneNumber,
    String rfc
) {}