package com.kbulkup.admin.service.user;

import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;

import java.util.List;

public interface AdminUserService {
    void createUser(SignupRequestDTO dto);
    UserResponseDTO getUserById(Long userId);
    List<UserResponseDTO> getAllUsers();
    void updateUser(Long userId, UserRequestDTO dto);
    void deleteUser(Long userId);
}