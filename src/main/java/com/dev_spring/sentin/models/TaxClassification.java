package com.dev_spring.sentin.models;

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
    name = "tax_classification",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_sat_code",
        columnNames = "sat_code"
    )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxClassification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classification")
    private Long idClassification;

    @Column(name = "sat_code", nullable = false, length = 3)
    private String satCode;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}