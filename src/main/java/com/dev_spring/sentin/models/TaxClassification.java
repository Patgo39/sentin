package com.dev_spring.sentin.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_classification",
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

  @NotBlank
  @Pattern(regexp = "^[1-9][0-9]{2}$", message = "sat code must be a 3 digit code")
  @Column(name = "sat_code", nullable = false, length = 3)
  private String satCode;

  @NotBlank
  @Size(max = 100)
  @Column(name = "name", nullable = false, length = 100)
  private String name;
    
}
