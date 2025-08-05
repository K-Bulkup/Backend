package com.kbulkup.admin.mapper;

import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminUserMapper {
    void insertUser(SignupRequestDTO requestDto);
    UserResponseDTO findUserById(Long userId);
    List<UserResponseDTO> findAllUsers();
    void updateUser(@Param("userId") Long userId, @Param("requestDto") UserRequestDTO requestDto);
    void deleteUser(Long userId);
    void insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    Long findRoleIdByRoleName(String roleName);
}
