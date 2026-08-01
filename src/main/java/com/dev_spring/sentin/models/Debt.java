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

    @Column(name = "debt_payment_type", nullable = false)
    private Short debtPaymentType;

    @Column(name = "interest_free")
    private Short interestFree;

    @Column(name = "payed", nullable = false)
    private boolean payed;
}