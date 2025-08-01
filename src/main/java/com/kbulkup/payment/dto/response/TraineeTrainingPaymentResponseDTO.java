package com.kbulkup.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingPaymentResponseDTO {

    private boolean success;
    private String message;
    private String paidAt;
    private String method;
    private String transactionId;

    public static TraineeTrainingPaymentResponseDTO ofSuccess(String paidAt, String method, String transactionId) {
        return new TraineeTrainingPaymentResponseDTO(true, "결제 성공", paidAt, method, transactionId);
    }

    public static TraineeTrainingPaymentResponseDTO ofFailure(String message) {
        return new TraineeTrainingPaymentResponseDTO(false, message, null, null, null);
    }

    public boolean isSuccess() {
        return success;
    }
}
