package com.kbulkup.payment.service;

import com.kbulkup.payment.client.PortOneClient;
import com.kbulkup.payment.domain.TraineeTrainingPayment;
import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;
import com.kbulkup.payment.mapper.TraineeTrainingPaymentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TraineeTrainingPaymentServiceImpl implements TraineeTrainingPaymentService {

    private final PortOneClient portOneClient;
    private final TraineeTrainingPaymentMapper paymentMapper;

    @Override
    @Transactional
    public TraineeTrainingPaymentResponseDTO processPayment(TraineeTrainingPaymentRequestDTO requestDTO) {

        //  1. 포트원 결제 검증
        var result = portOneClient.verifyPayment(requestDTO.getImpUid());

        if (!result.isPaid()) {
            return TraineeTrainingPaymentResponseDTO.ofFailure("결제 검증 실패");
        }

        //  2. 결제 엔티티 생성
        TraineeTrainingPayment payment = TraineeTrainingPayment.of(
                requestDTO.getUserId(),
                requestDTO.getTrainingId(),
                requestDTO.getImpUid(),
                requestDTO.getMerchantUid(),
                result.getAmount(),
                result.getMethod(),
                result.getPaidAt(),
                "PAID"
        );

        try {
            //  3. DB 저장
            paymentMapper.insertTraineeTrainingPayment(payment);
            paymentMapper.enrollUserToTraining(requestDTO.getUserId(), requestDTO.getTrainingId());
        } catch (Exception e) {
            return TraineeTrainingPaymentResponseDTO.ofFailure("DB 저장 중 오류");
        }

        //  4. 성공 응답
        return TraineeTrainingPaymentResponseDTO.ofSuccess(result, requestDTO.getImpUid());
    }
}
