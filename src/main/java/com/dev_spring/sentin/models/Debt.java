package com.dev_spring.sentin.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "debt")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Debt {

    @Id
    @Column(name = "id_expense")
    private Long idExpense;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id_expense")
    private Expense expense;

    @Column(name = "id_card")
    private Long idCard;

    @Column(name = "limit_date")
    private LocalDate limitDate;

    @NotNull
    @Min(0)
    @Max(1)
    @Column(name = "debt_payment_type", nullable = false)
    private Short debtPaymentType;

    @Min(1)
    @Column(name = "interest_free")
    private Short interestFree;

    @Column(name = "payed", nullable = false)
    private boolean payed;

    @AssertTrue(message = "A credit card is required if and only if the payment type is 1.")
    private boolean checkValidCreditState() {
        return (debtPaymentType == 0 && idCard == null)
            || (debtPaymentType == 1 && idCard != null);
    }

    @AssertTrue(message = "limit_date is required if the payment type is 1.")
    private boolean checkLimitDate() {
        return debtPaymentType == 0 || limitDate != null;
    }

    @AssertTrue(message = "A valid interest_free value is required if and only if the payment type is 1.")
    private boolean checkValidInterestFree() {
        return (debtPaymentType == 0 && interestFree == null)
            || (debtPaymentType == 1 && interestFree != null);
    }
}