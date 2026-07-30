package com.dev_spring.sentin.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "credit_card",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_user_card_alias",
        columnNames = {"id_user", "card_alias"}
    )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long idCard;

    @NotNull
    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @NotBlank
    @Size(max = 60)
    @Column(name = "card_alias", nullable = false, length = 60)
    private String cardAlias;

    @NotBlank
    @Size(max = 50)
    @Column(name = "bank", nullable = false, length = 50)
    private String bank;

    @Pattern(regexp = "^[0-9]{4}$", message = "last_four_digits must have exactly 4 digits")
    @Column(name = "last_four_digits", length = 4)
    private String lastFourDigits;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true, message = "interest_rate cannot be negative")
    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @NotNull
    @Min(1)
    @Max(31)
    @Column(name = "cut_off_day", nullable = false)
    private Short cutOffDay;

    @NotNull
    @Min(1)
    @Max(31)
    @Column(name = "payment_due_day", nullable = false)
    private Short paymentDueDay;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "credit_limit must be greater than 0")
    @Column(name = "credit_limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @DecimalMin(value = "0.0", inclusive = false, message = "user_credit_limit must be grater than 0 if specified")
    @Column(name = "user_credit_limit", precision = 10, scale = 2)
    private BigDecimal userCreditLimit;

    @AssertTrue(message = "user_credit_limit cannot exceed credit_limit")
    private boolean isUserCreditLimitValid() {
        return userCreditLimit == null
            || (creditLimit != null && userCreditLimit.compareTo(creditLimit) <= 0);
    }
}