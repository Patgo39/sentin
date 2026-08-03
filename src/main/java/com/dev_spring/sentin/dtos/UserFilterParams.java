package com.dev_spring.sentin.dtos;

import java.time.LocalDate;

public record UserFilterParams(
    String givenName,
    String middleName,
    String familyName,
    String username,
    LocalDate birthDate,
    String postalCode
) {}