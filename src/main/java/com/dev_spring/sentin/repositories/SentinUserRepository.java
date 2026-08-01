package com.dev_spring.sentin.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev_spring.sentin.models.SentinUser;

public interface SentinUserRepository extends JpaRepository<SentinUser, Long>{

}
