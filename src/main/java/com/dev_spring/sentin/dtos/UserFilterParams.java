package com.dev_spring.sentin.dtos;

public record UserFilterParams(
    String givenName,
    String middleName,
    String familyName,
    String username,
    Integer minAge,
    Integer maxAge,
    String postalCode,
    String rfc
) {}