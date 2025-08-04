package com.kbulkup.gpt.service;

import com.kbulkup.gpt.dto.response.GPTResponseDTO;

public interface GPTService {
    GPTResponseDTO requestOnlyText(String mission, String userAnswer);
    GPTResponseDTO requestImageAnalysis(String mission, String imageUrl);
    GPTResponseDTO requestCounseling(String userId, String question);
}
