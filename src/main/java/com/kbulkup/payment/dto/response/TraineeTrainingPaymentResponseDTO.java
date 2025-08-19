package com.kbulkup.payment.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(description = "트레이닝 결제 응답")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingPaymentResponseDTO {

    @ApiModelProperty(value = "결제 성공 여부")
    private boolean success;

    @ApiModelProperty(value = "메시지")
    private String message;

    @ApiModelProperty(value = "결제 승인 시각(ISO/PG 포맷)")
    private String paidAt;

    @ApiModelProperty(value = "결제 수단")
    private String method;

    @ApiModelProperty(value = "거래 ID/영수증 번호")
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
