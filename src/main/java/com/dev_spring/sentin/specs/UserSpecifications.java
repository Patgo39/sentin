package com.dev_spring.sentin.specs;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.dev_spring.sentin.dtos.UserFilterParams;
import com.dev_spring.sentin.models.SentinUser;

public class UserSpecifications {

    private UserSpecifications() {}

    public static Specification<SentinUser> givenNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("givenName")), "%" + value.trim().toLowerCase() + "%");
    }

    public static Specification<SentinUser> middleNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("middleName")), "%" + value.trim().toLowerCase() + "%");
    }

    public static Specification<SentinUser> familyNameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("familyName")), "%" + value.trim().toLowerCase() + "%");
    }

    public static Specification<SentinUser> usernameContains(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.like(cb.lower(root.get("username")), "%" + value.trim().toLowerCase() + "%");
    }

    public static Specification<SentinUser> hasBirthDate(LocalDate value) {
        return (root, query, cb) ->
            value == null ? null : cb.equal(root.get("birthDate"), value);
    }

    public static Specification<SentinUser> hasPostalCode(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.equal(root.get("postalCode"), value.trim());
    }

    public static Specification<SentinUser> hasRfc(String value) {
        return (root, query, cb) ->
            (value == null || value.isBlank()) ? null
                : cb.equal(root.get("rfc"), value.trim().toUpperCase());
    }

    /**
     * Combina todos los filtros opcionales de UserFilterParams en un solo Specification.
     * Los predicados que devuelven null (filtros ausentes) son ignorados por .and().
     */
    public static Specification<SentinUser> withFilters(UserFilterParams filters) {
        if (filters == null) {
            return null;
        }

        return Specification
            .where(givenNameContains(filters.givenName()))
            .and(middleNameContains(filters.middleName()))
            .and(familyNameContains(filters.familyName()))
            .and(usernameContains(filters.username()))
            .and(hasBirthDate(filters.birthDate()))
            .and(hasPostalCode(filters.postalCode()))
            .and(hasRfc(filters.rfc()));
    }
}