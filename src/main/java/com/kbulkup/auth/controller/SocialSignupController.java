package com.kbulkup.auth.controller;

import com.kbulkup.auth.dto.LoginResponseDTO;
import com.kbulkup.auth.dto.SocialSignupRequestDTO;
import com.kbulkup.auth.service.SocialSignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/social")
public class SocialSignupController {

    private final SocialSignupService socialSignupService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponseDTO> completeSocialSignup(@Valid @RequestBody SocialSignupRequestDTO requestDTO) {
        LoginResponseDTO response = socialSignupService.completeSignup(requestDTO);
        return ResponseEntity.ok(response);
    }
}
