package com.dev_spring.sentin.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "sentin_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_username", columnNames = "username"),
        @UniqueConstraint(name = "uq_email", columnNames = "email")
    }
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentinUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @NotBlank
    @Size(max = 20)
    @Column(name = "given_name", nullable = false, length = 20)
    private String givenName;

    @Size(max = 20)
    @Column(name = "middle_name", length = 20)
    private String middleName;

    @NotBlank
    @Size(max = 60)
    @Column(name = "family_name", nullable = false, length = 60)
    private String familyName;

    @NotBlank 
    @Size(max = 15)
    @Column(name = "username", nullable = false, length = 15)
    private String username;

    @NotBlank
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @NotBlank
    @Email
    @Size(max = 50)
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @NotNull
    @PastOrPresent(message = "birth_date cannot be a future date.")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Pattern(regexp = "^[0-9]{5}$", message = "postal_code must have 5 digits.")
    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$", message = "phone_number must have 10 digits.")
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @NotBlank
    @Size(min = 13, max = 13, message = "rfc must have 13 digits")
    @Column(name = "rfc", nullable = false, length = 13)
    private String rfc;

    @AssertTrue(message = "birth_date must be greater than 1900-01-01")
    private boolean isBirthDateWithinValidRange() {
        return birthDate == null || birthDate.isAfter(LocalDate.of(1900, 1, 1));
    }
}