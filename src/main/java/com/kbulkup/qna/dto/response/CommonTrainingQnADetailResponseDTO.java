package com.kbulkup.qna.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@ApiModel(description = "트레이닝 QnA 상세 항목")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonTrainingQnADetailResponseDTO {

    @ApiModelProperty(value = "QnA ID")
    private Long qnaId;

    @ApiModelProperty(value = "질문자 이름")
    private String userName;

    @ApiModelProperty(value = "질문 제목")
    private String questionTitle;

    @ApiModelProperty(value = "질문 본문")
    private String question;

    @ApiModelProperty(value = "답변 본문")
    private String answer;

    @ApiModelProperty(value = "질문 작성 시각")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "답변 작성 시각")
    private LocalDateTime answeredAt;

    public static CommonTrainingQnADetailResponseDTO create(Long qnaId, String userName, String question, String answer, LocalDateTime createAt, LocalDateTime answeredAt) {
        return CommonTrainingQnADetailResponseDTO.builder()
                .qnaId(qnaId)
                .userName(userName)
                .question(question)
                .answer(answer)
                .createAt(createAt)
                .answeredAt(answeredAt).build();
    }
}
