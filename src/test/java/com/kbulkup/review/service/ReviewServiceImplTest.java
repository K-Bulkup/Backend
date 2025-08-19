package com.kbulkup.review.service;

import com.kbulkup.review.dto.response.TrainerTrainingReviewDetailResponseDTO;
import com.kbulkup.review.dto.response.TrainingReviewSummaryResponseDTO;
import com.kbulkup.review.mapper.ReviewMapper;
import com.kbulkup.training.mapper.TrainingMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

/**
 * ReviewServiceImpl 단위 테스트
 * 이 클래스는 리뷰 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - 트레이닝 리뷰 조회
 * - 평균 평점 계산
 * - 리뷰 개수 집계
 * - 빈 리뷰 리스트 처리
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewMapper reviewMapper;

    @Mock
    private TrainingMapper trainingMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 성공 (다수의 리뷰)")
    void getTrainingReviews_WithMultipleReviews_Success() {
        // Given
        Long trainingId = 1L;
        
        List<TrainerTrainingReviewDetailResponseDTO> mockReviews = Arrays.asList(
            new TrainerTrainingReviewDetailResponseDTO("김회원", 5, "정말 좋은 운동이었습니다!"),
            new TrainerTrainingReviewDetailResponseDTO("이회원", 4, "전반적으로 만족스러워요"),
            new TrainerTrainingReviewDetailResponseDTO("박회원", 5, "트레이너가 친절하고 운동 효과가 좋았습니다")
        );
        
        Double avgRating = 4.7;
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(mockReviews);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(4.7);
        assertThat(result.getTotalReviewCount()).isEqualTo(3);
        assertThat(result.getReviews()).hasSize(3);
        
        // 첫 번째 리뷰 검증
        TrainerTrainingReviewDetailResponseDTO firstReview = result.getReviews().get(0);
        assertThat(firstReview.getUsername()).isEqualTo("김회원");
        assertThat(firstReview.getRating()).isEqualTo(5);
        assertThat(firstReview.getContent()).isEqualTo("정말 좋은 운동이었습니다!");
        
        // 두 번째 리뷰 검증
        TrainerTrainingReviewDetailResponseDTO secondReview = result.getReviews().get(1);
        assertThat(secondReview.getUsername()).isEqualTo("이회원");
        assertThat(secondReview.getRating()).isEqualTo(4);
        assertThat(secondReview.getContent()).isEqualTo("전반적으로 만족스러워요");
        
        // 세 번째 리뷰 검증
        TrainerTrainingReviewDetailResponseDTO thirdReview = result.getReviews().get(2);
        assertThat(thirdReview.getUsername()).isEqualTo("박회원");
        assertThat(thirdReview.getRating()).isEqualTo(5);
        assertThat(thirdReview.getContent()).isEqualTo("트레이너가 친절하고 운동 효과가 좋았습니다");

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 빈 리뷰 리스트")
    void getTrainingReviews_WithEmptyReviews_Success() {
        // Given
        Long trainingId = 2L;
        
        List<TrainerTrainingReviewDetailResponseDTO> emptyReviews = Collections.emptyList();
        Double avgRating = null; // 리뷰가 없으면 평균 평점도 null
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(emptyReviews);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(0.0); // null인 경우 0.0으로 설정
        assertThat(result.getTotalReviewCount()).isEqualTo(0);
        assertThat(result.getReviews()).isEmpty();

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 단일 리뷰")
    void getTrainingReviews_WithSingleReview_Success() {
        // Given
        Long trainingId = 3L;
        
        List<TrainerTrainingReviewDetailResponseDTO> singleReview = Arrays.asList(
            new TrainerTrainingReviewDetailResponseDTO("단일회원", 3, "보통 수준의 운동이었습니다")
        );
        
        Double avgRating = 3.0;
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(singleReview);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(3.0);
        assertThat(result.getTotalReviewCount()).isEqualTo(1);
        assertThat(result.getReviews()).hasSize(1);
        
        TrainerTrainingReviewDetailResponseDTO review = result.getReviews().get(0);
        assertThat(review.getUsername()).isEqualTo("단일회원");
        assertThat(review.getRating()).isEqualTo(3);
        assertThat(review.getContent()).isEqualTo("보통 수준의 운동이었습니다");

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 높은 평점의 리뷰들")
    void getTrainingReviews_WithHighRatings_Success() {
        // Given
        Long trainingId = 4L;
        
        List<TrainerTrainingReviewDetailResponseDTO> highRatingReviews = Arrays.asList(
            new TrainerTrainingReviewDetailResponseDTO("만족회원1", 5, "완벽한 트레이닝이었습니다!"),
            new TrainerTrainingReviewDetailResponseDTO("만족회원2", 5, "최고의 운동 경험!")
        );
        
        Double avgRating = 5.0;
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(highRatingReviews);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(5.0);
        assertThat(result.getTotalReviewCount()).isEqualTo(2);
        assertThat(result.getReviews()).hasSize(2);
        
        // 모든 리뷰가 5점인지 확인
        result.getReviews().forEach(review -> {
            assertThat(review.getRating()).isEqualTo(5);
            assertThat(review.getContent()).contains("!");
        });

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 낮은 평점의 리뷰들")
    void getTrainingReviews_WithLowRatings_Success() {
        // Given
        Long trainingId = 5L;
        
        List<TrainerTrainingReviewDetailResponseDTO> lowRatingReviews = Arrays.asList(
            new TrainerTrainingReviewDetailResponseDTO("불만회원1", 1, "기대에 못 미쳤습니다"),
            new TrainerTrainingReviewDetailResponseDTO("불만회원2", 2, "개선이 필요해요")
        );
        
        Double avgRating = 1.5;
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(lowRatingReviews);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(1.5);
        assertThat(result.getTotalReviewCount()).isEqualTo(2);
        assertThat(result.getReviews()).hasSize(2);
        
        // 첫 번째 리뷰 검증
        TrainerTrainingReviewDetailResponseDTO firstReview = result.getReviews().get(0);
        assertThat(firstReview.getUsername()).isEqualTo("불만회원1");
        assertThat(firstReview.getRating()).isEqualTo(1);
        assertThat(firstReview.getContent()).isEqualTo("기대에 못 미쳤습니다");
        
        // 두 번째 리뷰 검증
        TrainerTrainingReviewDetailResponseDTO secondReview = result.getReviews().get(1);
        assertThat(secondReview.getUsername()).isEqualTo("불만회원2");
        assertThat(secondReview.getRating()).isEqualTo(2);
        assertThat(secondReview.getContent()).isEqualTo("개선이 필요해요");

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }

    @Test
    @DisplayName("트레이닝 리뷰 조회 - 평균 평점이 0인 경우")
    void getTrainingReviews_WithZeroAverageRating_Success() {
        // Given
        Long trainingId = 6L;
        
        List<TrainerTrainingReviewDetailResponseDTO> zeroRatingReviews = Arrays.asList(
            new TrainerTrainingReviewDetailResponseDTO("특별회원", 0, "특별한 상황의 리뷰")
        );
        
        Double avgRating = 0.0;
        
        given(reviewMapper.findReviewsByTrainingId(trainingId)).willReturn(zeroRatingReviews);
        given(trainingMapper.findAverageRatingByTrainingId(trainingId)).willReturn(avgRating);

        // When
        TrainingReviewSummaryResponseDTO result = reviewService.getTrainingReviews(trainingId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAverageRating()).isEqualTo(0.0);
        assertThat(result.getTotalReviewCount()).isEqualTo(1);
        assertThat(result.getReviews()).hasSize(1);
        
        TrainerTrainingReviewDetailResponseDTO review = result.getReviews().get(0);
        assertThat(review.getUsername()).isEqualTo("특별회원");
        assertThat(review.getRating()).isEqualTo(0);
        assertThat(review.getContent()).isEqualTo("특별한 상황의 리뷰");

        // Mock 호출 검증
        then(reviewMapper).should().findReviewsByTrainingId(trainingId);
        then(trainingMapper).should().findAverageRatingByTrainingId(trainingId);
    }
}
