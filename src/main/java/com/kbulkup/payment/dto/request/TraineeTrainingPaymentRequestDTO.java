package com.kbulkup.payment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 결제 요청 시 프론트엔드에서 전달받는 데이터
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingPaymentRequestDTO {

    private String impUid;        // 포트원이 발급한 결제 고유 ID
    private String merchantUid;   // 서버에서 생성한 주문 번호
    private Long trainingId;      // 결제 대상 강의 ID
    private Long userId;          // 결제한 유저 ID
}
