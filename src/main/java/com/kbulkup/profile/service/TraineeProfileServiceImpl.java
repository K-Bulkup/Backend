package com.kbulkup.profile.service;

import com.kbulkup.common.exception.ProfileException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.profile.dto.response.TraineeProfileDetailResponseDTO;
import com.kbulkup.user.domain.User;
import com.kbulkup.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraineeProfileServiceImpl implements TraineeProfileService {

    private final UserMapper userMapper;

    @Override
    public TraineeProfileDetailResponseDTO getTraineeProfile(Long traineeId) {
        User user = userMapper.findById(traineeId).
                orElseThrow(() -> new ProfileException(ResponseCode.NOT_FOUND_TRAINEE_PROFILE));
        return TraineeProfileDetailResponseDTO.toDTO(user);
    }
}
