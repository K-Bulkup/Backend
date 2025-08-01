package com.kbulkup.payment.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;
import com.kbulkup.payment.service.TraineeTrainingPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/trainings")
public class TraineeTrainingPaymentController {

    //  @Qualifier로 구현체 지정
    @Qualifier("traineeTrainingPaymentServiceImpl")
    private final TraineeTrainingPaymentService paymentService;

    @PostMapping("/payment")
    public CustomResponse<TraineeTrainingPaymentResponseDTO> processPayment(
            @RequestBody TraineeTrainingPaymentRequestDTO requestDTO) {

        TraineeTrainingPaymentResponseDTO response = paymentService.processPayment(requestDTO);

        if (response.isSuccess()) {
            return CustomResponse.success(ResponseCode.SUCCESS, response);
        } else {
            return CustomResponse.error(ResponseCode.PAYMENT_VERIFICATION_FAILED, response);
        }
    }
}
