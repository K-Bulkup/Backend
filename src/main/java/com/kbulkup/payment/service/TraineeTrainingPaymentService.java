package com.kbulkup.payment.service;

import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;

public interface TraineeTrainingPaymentService {
    TraineeTrainingPaymentResponseDTO processPayment(TraineeTrainingPaymentRequestDTO requestDTO);
}
