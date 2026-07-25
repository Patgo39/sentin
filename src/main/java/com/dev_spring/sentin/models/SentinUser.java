package com.dev_spring.sentin.models;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sentin_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentinUser {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_user")
  private Long idUser;
  
  @Column(name = "given_name", nullable = false, length = 20)
  private String givenName;

  @Column(name = "middle_name", length = 20)
  private String middleName;

  @Column(name = "family_name", nullable = false, length = 60)
  private String familyName;

  @Column(name = "username", nullable = false, unique = true, length = 15)
  private String username;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "email", nullable = false, unique = true, length = 50)
  private String email;

  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @Column(name = "postal_code", nullable = false, length = 5)
  private String postalCode;

  @Column(name = "phone_number", nullable = false, length = 10)
  private String phoneNumber;

  @Column(name = "rfc", nullable = false, length = 13)
  private String rfc;
  
}