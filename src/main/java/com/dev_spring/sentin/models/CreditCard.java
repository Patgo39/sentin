package com.dev_spring.sentin.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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

    @Column(name = "id_user", nullable = false)
    private Long idUser;

    @Column(name = "card_alias", nullable = false, length = 60)
    private String cardAlias;

    @Column(name = "bank", nullable = false, length = 50)
    private String bank;

    @Column(name = "last_four_digits", length = 4)
    private String lastFourDigits;

    @Column(name = "interest_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;

    @Column(name = "cut_off_day", nullable = false)
    private Short cutOffDay;

    @Column(name = "payment_due_day", nullable = false)
    private Short paymentDueDay;

    @Column(name = "credit_limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal creditLimit;

    @Column(name = "user_credit_limit", precision = 10, scale = 2)
    private BigDecimal userCreditLimit;
}