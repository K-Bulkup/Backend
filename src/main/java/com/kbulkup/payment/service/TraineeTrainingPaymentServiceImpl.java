package com.kbulkup.payment.service;

import com.kbulkup.payment.client.PortOneClient;
import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;
import com.kbulkup.payment.mapper.TraineeTrainingPaymentMapper;
import com.kbulkup.payment.mapper.AuthUserMapper;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TraineeTrainingPaymentServiceImpl implements TraineeTrainingPaymentService {

    private final PortOneClient portOneClient;
    private final TraineeTrainingPaymentMapper paymentMapper;
    private final AuthUserMapper authUserMapper; // ✅ 이메일→user_id 조회

    private Long resolveUserId(Long fromClient) {
        if (fromClient != null && fromClient > 0) return fromClient;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            String email = auth.getName(); // 일반적으로 username=email
            if (email != null) {
                Long uid = authUserMapper.findUserIdByEmail(email);
                if (uid != null && uid > 0) return uid;
            }
        }
        // 최종 방어선: 0 반환하지 않음
        throw new IllegalStateException("인증 사용자 ID를 확인할 수 없습니다.");
    }

    @Override
    public TraineeTrainingPaymentResponseDTO processPayment(TraineeTrainingPaymentRequestDTO requestDTO, User user) {

        final Long userId = user.getUserId();
        final Long trainingId = requestDTO.getTrainingId();
        log.info("[Payment] userId={}, trainingId={}, impUid={}", userId, trainingId, requestDTO.getImpUid());

        // ✅ 이미 등록되어 있으면 성공 취급
        Long exists = paymentMapper.findEnrollmentId(userId, trainingId);
        if (exists != null) {
            return TraineeTrainingPaymentResponseDTO.ofSuccess(
                    null,
                    "ALREADY_ENROLLED",
                    requestDTO.getImpUid()
            );
        }

        // 1) 포트원 결제 검증
        var result = portOneClient.verifyPayment(requestDTO.getImpUid());
        if (!result.isPaid()) {
            return TraineeTrainingPaymentResponseDTO.ofFailure("결제 검증 실패");
        }

        // 2) enrollments 신규 등록
        paymentMapper.insertEnrollment(userId, trainingId);

        // 3) 새로 생성된 enrollment_id 조회
        Long enrollmentId = paymentMapper.findEnrollmentId(userId, trainingId);

        // 4) routine_results 초기화
        paymentMapper.insertRoutineResultsForEnrollment(enrollmentId, trainingId);

        // 5) 성공 응답 반환
        return TraineeTrainingPaymentResponseDTO.ofSuccess(
                result.getPaidAt(),
                result.getMethod(),
                requestDTO.getImpUid()
        );
    }
}
