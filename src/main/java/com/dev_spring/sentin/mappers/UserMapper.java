package com.dev_spring.sentin.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.dev_spring.sentin.dtos.ChangeEmailRequest;
import com.dev_spring.sentin.dtos.CreateUserRequest;
import com.dev_spring.sentin.dtos.UserResponse;
import com.dev_spring.sentin.models.SentinUser;

@Mapper
public interface UserMapper {

  // Create the user and ignores the id. It'll be created by postgres
  @Mapping(target = "idUser", ignore = true)
  SentinUser toEntity(CreateUserRequest request);

  UserResponse toResponse(SentinUser entity);

  List<UserResponse> toResponseList(List<SentinUser> entities);

  @Mapping(target = "email", source = "request.newEmail")
  void updateEmailFromRequest(ChangeEmailRequest request, @MappingTarget SentinUser entity);

}
