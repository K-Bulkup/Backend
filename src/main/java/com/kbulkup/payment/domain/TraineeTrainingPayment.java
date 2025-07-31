package com.kbulkup.payment.domain;

import lombok.Getter;

/**
 * training_payment 테이블과 매핑되는 결제 엔티티
 */
@Getter
public class TraineeTrainingPayment {

    private Long userId;
    private Long trainingId;
    private String impUid;
    private String merchantUid;
    private int amount;
    private String method;
    private String paidAt;
    private String status;

    private TraineeTrainingPayment(Long userId, Long trainingId, String impUid, String merchantUid,
                                   int amount, String method, String paidAt, String status) {
        this.userId = userId;
        this.trainingId = trainingId;
        this.impUid = impUid;
        this.merchantUid = merchantUid;
        this.amount = amount;
        this.method = method;
        this.paidAt = paidAt;
        this.status = status;
    }

    /**
     * 팩토리 메서드
     */
    public static TraineeTrainingPayment of(Long userId, Long trainingId, String impUid, String merchantUid,
                                            int amount, String method, String paidAt, String status) {
        return new TraineeTrainingPayment(userId, trainingId, impUid, merchantUid, amount, method, paidAt, status);
    }
}
