package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.SignupRequestDTO;
import com.kbulkup.auth.dto.SignupResponseDTO;
import com.kbulkup.auth.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/api/common/auth/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO requestDTO) {
        SignupResponseDTO responseDTO = signupService.signup(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
