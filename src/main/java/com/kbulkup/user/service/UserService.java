package com.kbulkup.user.service;

import com.kbulkup.user.dto.response.UserResponseDTO;

public interface UserService {

    UserResponseDTO getUserFromToken(String token);
}
