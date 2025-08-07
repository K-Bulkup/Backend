package com.kbulkup.admin.service.user;

import com.kbulkup.admin.mapper.AdminUserMapper;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.common.exception.AdminException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(SignupRequestDTO dto) {
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encodedPassword);
        adminUserMapper.insertUser(dto);
        Long roleId = adminUserMapper.findRoleIdByRoleName(dto.getRole());
        if (roleId != null) {
            adminUserMapper.insertUserRole(dto.getUserId(), roleId);
        } else {
            Long defaultRoleId = adminUserMapper.findRoleIdByRoleName("TRAINEE");
            if (defaultRoleId != null) {
                adminUserMapper.insertUserRole(dto.getUserId(), defaultRoleId);
            } else {
                throw new AdminException(ResponseCode.ADMIN_USER_ROLE_MISSING);
            }
        }
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        return adminUserMapper.findUserById(userId);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return adminUserMapper.findAllUsers();
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserRequestDTO dto) {
        adminUserMapper.updateUser(userId, dto);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        adminUserMapper.deleteUser(userId);
    }
}
