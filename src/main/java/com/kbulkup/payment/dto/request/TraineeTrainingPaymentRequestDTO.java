package com.kbulkup.payment.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@ApiModel(description = "트레이닝 결제 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingPaymentRequestDTO {

    @ApiModelProperty(value = "PG 결제 고유번호(예: 아임포트 imp_uid)", required = true)
    private String impUid;

    @ApiModelProperty(value = "가맹점 주문번호(merchant_uid)", required = true)
    private String merchantUid;

    @ApiModelProperty(value = "트레이닝 ID", required = true, example = "2001")
    private Long trainingId;

    @ApiModelProperty(
            value = "사용자 ID(서버는 인증 사용자 기준으로 처리, 클라이언트 값은 사용/검증하지 않을 수 있음)",
            required = false,
            example = "1001"
    )
    private Long userId;
}
