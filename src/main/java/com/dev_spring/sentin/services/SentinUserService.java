package com.dev_spring.sentin.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; 

import com.dev_spring.sentin.dtos.ChangeEmailRequest;
import com.dev_spring.sentin.dtos.ChangePasswordRequest;
import com.dev_spring.sentin.dtos.CreateUserRequest;
import com.dev_spring.sentin.dtos.UpdateUserProfileRequest;
import com.dev_spring.sentin.dtos.UserFilterParams;
import com.dev_spring.sentin.dtos.UserResponse;

public interface SentinUserService {
  Long registerUser(CreateUserRequest request);
  void updateUserProfile(Long userId, UpdateUserProfileRequest request);
  void changeUserPassword(Long userId, ChangePasswordRequest request);
  void changeUserEmail(Long userId, ChangeEmailRequest request);
  Page<UserResponse> filterUsers(UserFilterParams filterParams, Pageable pageable);
  void deleteUser(Long userId);
  UserResponse getUserById(Long userId);
}