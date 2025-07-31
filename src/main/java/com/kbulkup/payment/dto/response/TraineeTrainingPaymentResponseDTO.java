package com.kbulkup.payment.dto.response;

import com.kbulkup.payment.client.PortOneClient;
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

    public static TraineeTrainingPaymentResponseDTO ofSuccess(PortOneClient.PaymentResult result, String impUid) {
        return new TraineeTrainingPaymentResponseDTO(true, "결제 성공", result.getPaidAt(), result.getMethod(), impUid);
    }

    public static TraineeTrainingPaymentResponseDTO ofFailure(String message) {
        return new TraineeTrainingPaymentResponseDTO(false, message, null, null, null);
    }
}
