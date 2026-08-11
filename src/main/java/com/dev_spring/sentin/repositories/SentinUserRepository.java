package com.dev_spring.sentin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.dev_spring.sentin.models.SentinUser;

public interface SentinUserRepository extends JpaRepository<SentinUser, Long>, JpaSpecificationExecutor<SentinUser> {

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);
}
