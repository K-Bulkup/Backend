package com.kbulkup.payment.service;

import com.kbulkup.payment.client.PortOneClient;
import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;
import com.kbulkup.payment.mapper.TraineeTrainingPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TraineeTrainingPaymentServiceImpl implements TraineeTrainingPaymentService {

    private final PortOneClient portOneClient;
    private final TraineeTrainingPaymentMapper paymentMapper;

    @Override
    public TraineeTrainingPaymentResponseDTO processPayment(TraineeTrainingPaymentRequestDTO requestDTO) {
        //  1. 포트원 결제 검증
        var result = portOneClient.verifyPayment(requestDTO.getImpUid());

        if (!result.isPaid()) {
            return TraineeTrainingPaymentResponseDTO.ofFailure("결제 검증 실패");
        }

        //  2. enrollments 테이블에 신규 등록
        paymentMapper.insertEnrollment(requestDTO.getUserId(), requestDTO.getTrainingId());

        //  3. 성공 응답 반환
        return TraineeTrainingPaymentResponseDTO.ofSuccess(
                result.getPaidAt(),
                result.getMethod(),
                requestDTO.getImpUid()
        );
    }
}
