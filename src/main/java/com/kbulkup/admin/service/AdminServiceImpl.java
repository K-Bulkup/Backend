package com.kbulkup.admin.service;

import com.kbulkup.admin.mapper.AdminTrainingMapper;
import com.kbulkup.admin.mapper.AdminUserMapper;
import com.kbulkup.admin.dto.response.AdminTrainingResponseDto;
import com.kbulkup.auth.dto.request.SignupRequestDTO;
import com.kbulkup.user.dto.request.UserRequestDTO;
import com.kbulkup.user.dto.response.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminTrainingMapper adminTrainingMapper;
    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AdminTrainingResponseDto> getAllTrainings() {
        return adminTrainingMapper.findAllTrainings();
    }

    @Override
    public List<AdminTrainingResponseDto> getApprovedTrainings() {
        return adminTrainingMapper.findApprovedTrainings();
    }

    @Override
    public List<AdminTrainingResponseDto> getPendingTrainingsForAdmin() {
        return adminTrainingMapper.findPendingTrainings();
    }

    @Override
    @Transactional
    public void approveTraining(Long trainingId) {
        adminTrainingMapper.updateTrainingApprovalStatus(trainingId, "승인");
    }

    @Override
    @Transactional
    public void rejectTraining(Long trainingId) {
        adminTrainingMapper.updateTrainingApprovalStatus(trainingId, "거부");
    }

    @Override
    @Transactional
    public void createUser(SignupRequestDTO requestDto) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        requestDto.setPassword(encodedPassword);

        // 사용자 정보 삽입
        adminUserMapper.insertUser(requestDto);

        // 사용자 역할 삽입
        Long roleId = adminUserMapper.findRoleIdByRoleName(requestDto.getRole());
        if (roleId != null) {
            adminUserMapper.insertUserRole(requestDto.getUserId(), roleId);
        } else {
            // 역할이 없는 경우 기본 역할(예: TRAINEE)을 할당하거나 예외 처리
            // 여기서는 TRAINEE 역할을 기본으로 할당하는 예시
            Long defaultRoleId = adminUserMapper.findRoleIdByRoleName("TRAINEE");
            if (defaultRoleId != null) {
                adminUserMapper.insertUserRole(requestDto.getUserId(), defaultRoleId);
            } else {
                throw new RuntimeException("기본 역할을 찾을 수 없습니다.");
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
    public void updateUser(Long userId, UserRequestDTO requestDto) {
        adminUserMapper.updateUser(userId, requestDto);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        adminUserMapper.deleteUser(userId);
    }
}
