package com.dev_spring.sentin.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.dev_spring.sentin.dtos.UserFilterParams;
import com.dev_spring.sentin.models.SentinUser;

public class UserSpecifications {

    private UserSpecifications() {}

    private static Specification<SentinUser> givenNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("givenName")), "%" + value.trim().toLowerCase() + "%");
    }

    private static Specification<SentinUser> middleNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("middleName")), "%" + value.trim().toLowerCase() + "%");
    }

    private static Specification<SentinUser> familyNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("familyName")), "%" + value.trim().toLowerCase() + "%");
    }

    private static Specification<SentinUser> usernameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("username")), "%" + value.trim().toLowerCase() + "%");
    }

    private static Specification<SentinUser> hasMinAge(Integer minAge) {
        return (root, query, cb) -> {
        
        if (minAge == null) {
                return null;
            }

            LocalDate today = LocalDate.now();
            LocalDate maxBirthDate = today.minusYears(minAge);

            return cb.lessThanOrEqualTo(root.get("birthDate"), maxBirthDate);
        };
    }

    private static Specification<SentinUser> hasMaxAge(Integer maxAge) {
        return (root, query, cb) -> {
        
        if (maxAge == null) {
                return null;
            }

            LocalDate today = LocalDate.now();
            LocalDate maxBirthDate = today.minusYears(maxAge);

            return cb.greaterThanOrEqualTo(root.get("birthDate"), maxBirthDate);
        };
    }

    private static Specification<SentinUser> hasPostalCode(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.equal(root.get("postalCode"), value.trim());
    }

    private static Specification<SentinUser> hasRfc(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.equal(root.get("rfc"), value.trim().toUpperCase());
    }

    /**
     * Combines all optional filters.
     */
    public static Specification<SentinUser> getQueryWithFilters(UserFilterParams filters) {
        if (filters == null) {
            return null;
        }

        if(filters.minAge() != null && filters.maxAge() != null && filters.minAge() > filters.maxAge()) {
            throw new IllegalArgumentException("minAge cannot be greater than maxAge");
        }

        return Specification
            .where(givenNameContains(filters.givenName()))
            .and(middleNameContains(filters.middleName()))
            .and(familyNameContains(filters.familyName()))
            .and(usernameContains(filters.username()))
            .and(hasMinAge(filters.minAge()))
            .and(hasMaxAge(filters.maxAge()))
            .and(hasPostalCode(filters.postalCode()))
            .and(hasRfc(filters.rfc()));
    }
}