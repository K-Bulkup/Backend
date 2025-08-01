package com.kbulkup.payment.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TraineeTrainingPaymentRequestDTO {
    private String impUid;
    private String merchantUid;
    private Long trainingId;
    private Long userId;
}
