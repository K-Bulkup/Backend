package com.kbulkup.training.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.kbulkup.routine.domain.Routine;
import com.kbulkup.routine.domain.RoutineAnswer;
import com.kbulkup.training.domain.Training;
import com.kbulkup.training.dto.request.TrainerTrainingCreateRequestDTO;
import com.kbulkup.training.mapper.TrainingMapper;
import com.kbulkup.training.mapper.TrainingRoutineMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

/**
 * TrainingServiceImpl 단위 테스트
 * 이 클래스는 트레이닝 서비스의 핵심 비즈니스 로직을 테스트합니다:
 * - 트레이닝 생성 (썸네일 업로드 포함)
 * - 루틴 생성 및 점수 계산
 * - 가격 계산 로직
 * - S3 파일 업로드 처리
 * - 루틴 답변 처리
 */
@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private TrainingRoutineMapper trainingRoutineMapper;

    @Mock
    private AmazonS3 amazonS3;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    @DisplayName("썸네일과 함께 트레이닝 생성 - 성공")
    void createTraining_WithThumbnail_Success() throws IOException {
        // Given
        Long trainerId = 1L;
        String bucketName = "test-bucket";
        
        // Set private field using ReflectionTestUtils
        ReflectionTestUtils.setField(trainingService, "bucket", bucketName);

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = Arrays.asList(
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Squat", "Leg strength exercise", "근력", "multiple-choice", 1, null, "https://example.com/squat.mp4", "Keep knees behind toes"
                ),
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Running", "Cardio exercise", "유산소", "essay", 2, null, null, "Maintain steady pace"
                )
        );

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Basic Fitness", "Basic exercise for beginners", "Fitness", "초급", routines
        );

        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnail", "test.jpg", "image/jpeg", "test image content".getBytes()
        );

        // S3 mocking
        URL mockUrl = new URL("https://test-bucket.s3.amazonaws.com/trainings/test-uuid-test.jpg");
        given(amazonS3.getUrl(eq(bucketName), anyString())).willReturn(mockUrl);

        // Training ID setting simulation
        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 1L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        // Routine ID setting simulation
        willAnswer(invocation -> {
            Routine routine = invocation.getArgument(0);
            ReflectionTestUtils.setField(routine, "routineId", 1L);
            return null;
        }).given(trainingRoutineMapper).createRoutine(any(Routine.class));

        // When
        trainingService.createTraining(trainerId, dto, thumbnail);

        // Then
        // Verify S3 upload with any InputStream (since it's created new each time)
        then(amazonS3).should().putObject(eq(bucketName), startsWith("trainings/"), 
                any(InputStream.class), any(ObjectMetadata.class));

        // Verify Training save
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        then(trainingMapper).should().createTraining(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();
        assertThat(capturedTraining.getTrainerId()).isEqualTo(trainerId);
        assertThat(capturedTraining.getTitle()).isEqualTo("Basic Fitness");
        assertThat(capturedTraining.getDescription()).isEqualTo("Basic exercise for beginners");
        assertThat(capturedTraining.getCategory()).isEqualTo("Fitness");
        assertThat(capturedTraining.getLevel()).isEqualTo("초급");
        assertThat(capturedTraining.getPrice()).isEqualTo(5000); // Beginner price
        assertThat(capturedTraining.getTotalScore()).isEqualTo(5); // strength(2) + cardio(3)
        assertThat(capturedTraining.getThumbnailUrl()).isEqualTo(mockUrl.toString());
        assertThat(capturedTraining.getApprovalStatus()).isEqualTo("대기");

        // Verify Routine save (2 routines)
        ArgumentCaptor<Routine> routineCaptor = ArgumentCaptor.forClass(Routine.class);
        then(trainingRoutineMapper).should(times(2)).createRoutine(routineCaptor.capture());

        List<Routine> capturedRoutines = routineCaptor.getAllValues();
        
        // First routine (Squat - strength)
        Routine firstRoutine = capturedRoutines.get(0);
        assertThat(firstRoutine.getTrainingId()).isEqualTo(1L);
        assertThat(firstRoutine.getTitle()).isEqualTo("Squat");
        assertThat(firstRoutine.getRoutineType()).isEqualTo("근력");
        assertThat(firstRoutine.getScore()).isEqualTo(2); // Beginner strength score

        // Second routine (Running - cardio)
        Routine secondRoutine = capturedRoutines.get(1);
        assertThat(secondRoutine.getTitle()).isEqualTo("Running");
        assertThat(secondRoutine.getRoutineType()).isEqualTo("유산소");
        assertThat(secondRoutine.getScore()).isEqualTo(3); // Beginner cardio score

        // Verify video URL save (first routine only)
        then(trainingRoutineMapper).should().createRoutineVideo(1L, "https://example.com/squat.mp4");

        // Verify routine answer save (2 answers)
        ArgumentCaptor<RoutineAnswer> answerCaptor = ArgumentCaptor.forClass(RoutineAnswer.class);
        then(trainingRoutineMapper).should(times(2)).createRoutineAnswer(answerCaptor.capture());

        List<RoutineAnswer> capturedAnswers = answerCaptor.getAllValues();
        assertThat(capturedAnswers.get(0).getAnswer()).isEqualTo("Keep knees behind toes");
        assertThat(capturedAnswers.get(1).getAnswer()).isEqualTo("Maintain steady pace");
    }

    @Test
    @DisplayName("썸네일 없이 트레이닝 생성 - 성공")
    void createTraining_WithoutThumbnail_Success() throws IOException {
        // Given
        Long trainerId = 2L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = Arrays.asList(
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Stretching", "Neck stretching", "스트레칭", "essay", 1, null, null, "Stretch slowly"
                )
        );

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Neck Health", "Neck stretching for office workers", "Healthcare", "초급", routines
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 2L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        willAnswer(invocation -> {
            Routine routine = invocation.getArgument(0);
            ReflectionTestUtils.setField(routine, "routineId", 2L);
            return null;
        }).given(trainingRoutineMapper).createRoutine(any(Routine.class));

        // When
        trainingService.createTraining(trainerId, dto, null);

        // Then
        // Verify S3 upload was not called
        then(amazonS3).should(never()).putObject(anyString(), anyString(), any(), any(ObjectMetadata.class));

        // Verify Training save
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        then(trainingMapper).should().createTraining(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();
        assertThat(capturedTraining.getThumbnailUrl()).isNull();
        assertThat(capturedTraining.getTotalScore()).isEqualTo(1); // Stretching beginner score

        // Verify video URL save was not called
        then(trainingRoutineMapper).should(never()).createRoutineVideo(anyLong(), anyString());
    }

    @Test
    @DisplayName("중급 트레이닝 생성 - 정확한 가격 및 점수 계산")
    void createTraining_IntermediateLevel_CorrectPriceAndScore() throws IOException {
        // Given
        Long trainerId = 3L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = Arrays.asList(
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Plank", "Core strength exercise", "근력", "multiple-choice", 1, null, null, "Keep body straight"
                ),
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Dynamic Stretching", "Pre-workout warmup", "스트레칭", "essay", 2, null, null, "Warm up sufficiently"
                ),
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Cycling", "Indoor cardio", "유산소", "essay", 3, null, null, "Manage heart rate"
                )
        );

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Intermediate Comprehensive", "Complex exercise for intermediate", "Fitness", "중급", routines
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 3L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        // When
        trainingService.createTraining(trainerId, dto, null);

        // Then
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        then(trainingMapper).should().createTraining(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();
        assertThat(capturedTraining.getPrice()).isEqualTo(10000); // Intermediate price
        assertThat(capturedTraining.getTotalScore()).isEqualTo(10); // strength(3) + stretching(2) + cardio(5)
        assertThat(capturedTraining.getLevel()).isEqualTo("중급");
    }

    @Test
    @DisplayName("고급 트레이닝 생성 - 정확한 가격 및 점수 계산")
    void createTraining_AdvancedLevel_CorrectPriceAndScore() throws IOException {
        // Given
        Long trainerId = 4L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = Arrays.asList(
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Deadlift", "Full body strength", "근력", "multiple-choice", 1, null, null, "Keep back straight"
                ),
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "HIIT", "High intensity interval", "유산소", "essay", 2, null, null, "Maximum intensity"
                )
        );

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Advanced Power Training", "High intensity for advanced", "Power Training", "고급", routines
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 4L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        // When
        trainingService.createTraining(trainerId, dto, null);

        // Then
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        then(trainingMapper).should().createTraining(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();
        assertThat(capturedTraining.getPrice()).isEqualTo(20000); // Advanced price
        assertThat(capturedTraining.getTotalScore()).isEqualTo(12); // strength(5) + cardio(7)
        assertThat(capturedTraining.getLevel()).isEqualTo("고급");
    }

    @Test
    @DisplayName("루틴 없이 트레이닝 생성 - 성공")
    void createTraining_NoRoutines_Success() throws IOException {
        // Given
        Long trainerId = 5L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Theory Course", "Exercise theory only", "Education", "초급", null
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 5L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        // When
        trainingService.createTraining(trainerId, dto, null);

        // Then
        ArgumentCaptor<Training> trainingCaptor = ArgumentCaptor.forClass(Training.class);
        then(trainingMapper).should().createTraining(trainingCaptor.capture());

        Training capturedTraining = trainingCaptor.getValue();
        assertThat(capturedTraining.getTotalScore()).isEqualTo(0); // No routines

        // Verify routine related methods were not called
        then(trainingRoutineMapper).should(never()).createRoutine(any(Routine.class));
        then(trainingRoutineMapper).should(never()).createRoutineVideo(anyLong(), anyString());
        then(trainingRoutineMapper).should(never()).createRoutineAnswer(any(RoutineAnswer.class));
    }

    @Test
    @DisplayName("S3 업로드 실패 - 예외 처리")
    void createTraining_S3UploadFails_ThrowsException() {
        // Given
        Long trainerId = 6L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Test Training", "Description", "Category", "초급", Arrays.asList()
        );

        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnail", "test.jpg", "image/jpeg", "test".getBytes()
        );

        // S3 upload failure simulation
        given(amazonS3.putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class)))
                .willThrow(new RuntimeException("S3 upload failed"));

        // When & Then
        assertThatThrownBy(() -> trainingService.createTraining(trainerId, dto, thumbnail))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("S3 upload failed");

        // Verify Training was not saved
        then(trainingMapper).should(never()).createTraining(any(Training.class));
    }

    @Test
    @DisplayName("빈 썸네일 파일 처리")
    void createTraining_EmptyThumbnail_SkipsUpload() throws IOException {
        // Given
        Long trainerId = 7L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Test Training", "Description", "Category", "초급", Arrays.asList()
        );

        MockMultipartFile emptyThumbnail = new MockMultipartFile(
                "thumbnail", "", "image/jpeg", new byte[0]
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 7L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        // When
        trainingService.createTraining(trainerId, dto, emptyThumbnail);

        // Then
        // Verify S3 upload was not called
        then(amazonS3).should(never()).putObject(anyString(), anyString(), any(), any(ObjectMetadata.class));

        // Verify Training was saved
        then(trainingMapper).should().createTraining(any(Training.class));
    }

    @Test
    @DisplayName("공백 루틴 답변 처리")
    void createTraining_BlankRoutineAnswer_SkipsAnswerCreation() throws IOException {
        // Given
        Long trainerId = 8L;
        ReflectionTestUtils.setField(trainingService, "bucket", "test-bucket");

        List<TrainerTrainingCreateRequestDTO.RoutineDTO> routines = Arrays.asList(
                TrainerTrainingCreateRequestDTO.RoutineDTO.of(
                        "Test Routine", "Description", "근력", "multiple-choice", 1, null, null, "   " // Blank answer
                )
        );

        TrainerTrainingCreateRequestDTO dto = TrainerTrainingCreateRequestDTO.of(
                "Test Training", "Description", "Category", "초급", routines
        );

        willAnswer(invocation -> {
            Training training = invocation.getArgument(0);
            ReflectionTestUtils.setField(training, "trainingId", 8L);
            return null;
        }).given(trainingMapper).createTraining(any(Training.class));

        willAnswer(invocation -> {
            Routine routine = invocation.getArgument(0);
            ReflectionTestUtils.setField(routine, "routineId", 8L);
            return null;
        }).given(trainingRoutineMapper).createRoutine(any(Routine.class));

        // When
        trainingService.createTraining(trainerId, dto, null);

        // Then
        // Verify routine was created but answer was not
        then(trainingRoutineMapper).should().createRoutine(any(Routine.class));
        then(trainingRoutineMapper).should(never()).createRoutineAnswer(any(RoutineAnswer.class));
    }
}
