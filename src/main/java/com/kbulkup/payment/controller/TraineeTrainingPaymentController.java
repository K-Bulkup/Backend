package com.kbulkup.payment.controller;

import com.kbulkup.common.response.CustomResponse;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.payment.dto.request.TraineeTrainingPaymentRequestDTO;
import com.kbulkup.payment.dto.response.TraineeTrainingPaymentResponseDTO;
import com.kbulkup.payment.service.TraineeTrainingPaymentService;
import com.kbulkup.user.domain.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Payment (Trainee)", description = "수강생 트레이닝 결제 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainee/trainings")
public class TraineeTrainingPaymentController {

    //  @Qualifier로 구현체 지정
    @Qualifier("traineeTrainingPaymentServiceImpl")
    private final TraineeTrainingPaymentService paymentService;

    @ApiOperation(
            value = "트레이닝 결제 처리",
            notes = "PG 응답(impUid/merchantUid) 기반으로 결제를 검증하고 처리합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(응답 바디의 success 로 최종 결과 확인)"),
    })
    @PostMapping("/payment")
    public CustomResponse<TraineeTrainingPaymentResponseDTO> processPayment(
            @ApiParam(value = "결제 요청 바디", required = true)
            @RequestBody TraineeTrainingPaymentRequestDTO requestDTO,
            @ApiIgnore @AuthenticationPrincipal(expression = "user") User user) {

        TraineeTrainingPaymentResponseDTO response = paymentService.processPayment(requestDTO, user);

        if (response.isSuccess()) {
            return CustomResponse.success(ResponseCode.SUCCESS, response);
        } else {
            return CustomResponse.error(ResponseCode.PAYMENT_VERIFICATION_FAILED, response);
        }
    }
}
