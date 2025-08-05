package com.kbulkup.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainingReviewDetailResponseDTO {
    private String username; //회원 닉네임
    private int rating; //평점
    private String content; //리뷰 내용
}
