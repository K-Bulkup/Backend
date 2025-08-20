package com.kbulkup.gpt.service;

import com.kbulkup.chat.service.AiChatService;
import com.kbulkup.gpt.dto.common.MessageDTO;
import com.kbulkup.gpt.dto.request.GPTRequestDTO;
import com.kbulkup.gpt.dto.response.ChoiceDTO;
import com.kbulkup.gpt.dto.response.GPTResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

/**
 * GPTServiceImpl Unit Test
 * This class tests the core business logic of GPT service:
 * - Text-only request processing
 * - Image analysis request processing
 * - Counseling request with chat saving
 * - Prompt building and API communication
 * - Response handling and modification
 */
@ExtendWith(MockitoExtension.class)
class GPTServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AiChatService aiChatService;

    @InjectMocks
    private GPTServiceImpl gptService;

    private String apiModel;
    private String apiUrl;

    @BeforeEach
    void setUp() {
        apiModel = "gpt-4";
        apiUrl = "https://api.openai.com/v1/chat/completions";
        
        ReflectionTestUtils.setField(gptService, "apiModel", apiModel);
        ReflectionTestUtils.setField(gptService, "apiUrl", apiUrl);
    }

    @Test
    @DisplayName("Text only request - Success")
    void requestOnlyText_Success() {
        // Given
        String mission = "Write a simple hello world program";
        String userAnswer = "System.out.println(\"Hello World\");";

        GPTResponseDTO expectedResponse = createMockGPTResponse("true");

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(expectedResponse);

        // When
        GPTResponseDTO result = gptService.requestOnlyText(mission, userAnswer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-id");
        assertThat(result.getChoices()).hasSize(1);
        assertThat(result.getChoices().get(0).getMessage().getContent()).isEqualTo("true");

        // Verify RestTemplate call
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.getModel()).isEqualTo(apiModel);
        assertThat(capturedRequest.getMaxTokens()).isEqualTo(300);
        assertThat(capturedRequest.getMessages()).hasSize(1);
        
        MessageDTO message = capturedRequest.getMessages().get(0);
        assertThat(message.getRole()).isEqualTo("user");
        assertThat(message.getContent().toString()).contains(mission);
        assertThat(message.getContent().toString()).contains(userAnswer);
    }

    @Test
    @DisplayName("Image analysis request - Success")
    void requestImageAnalysis_Success() {
        // Given
        String mission = "Check if the exercise form is correct";
        String imageUrl = "https://example.com/exercise.jpg";

        GPTResponseDTO expectedResponse = createMockGPTResponse("false");

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(expectedResponse);

        // When
        GPTResponseDTO result = gptService.requestImageAnalysis(mission, imageUrl);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-id");
        assertThat(result.getChoices()).hasSize(1);
        assertThat(result.getChoices().get(0).getMessage().getContent()).isEqualTo("false");

        // Verify RestTemplate call
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.getModel()).isEqualTo(apiModel);
        assertThat(capturedRequest.getMaxTokens()).isEqualTo(500);
        assertThat(capturedRequest.getMessages()).hasSize(1);
        
        MessageDTO message = capturedRequest.getMessages().get(0);
        assertThat(message.getRole()).isEqualTo("user");
        // Image analysis uses List content structure
        assertThat(message.getContent()).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Counseling request - General question")
    void requestCounseling_GeneralQuestion_Success() {
        // Given
        String userId = "user123";
        String question = "What's the best investment strategy for beginners?";
        boolean isAsset = false;

        String gptAnswer = "For beginners, I recommend starting with index funds and diversifying your portfolio.";
        GPTResponseDTO gptResponse = createMockGPTResponse(gptAnswer);
        int remainingChats = 8;

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(gptResponse);
        given(aiChatService.saveAiChatMessage(userId, question, "user")).willReturn(0);
        given(aiChatService.saveAiChatMessage(userId, gptAnswer, "assistant")).willReturn(remainingChats);

        // When
        GPTResponseDTO result = gptService.requestCounseling(userId, question, isAsset);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("test-id");
        assertThat(result.getRemainingChats()).isEqualTo(remainingChats);
        assertThat(result.getChoices().get(0).getMessage().getContent()).isEqualTo(gptAnswer);

        // Verify AI chat service calls
        then(aiChatService).should().saveAiChatMessage(userId, question, "user");
        then(aiChatService).should().saveAiChatMessage(userId, gptAnswer, "assistant");

        // Verify RestTemplate call
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.getModel()).isEqualTo(apiModel);
        assertThat(capturedRequest.getMaxTokens()).isEqualTo(500);
    }

    @Test
    @DisplayName("Text request - False evaluation")
    void requestOnlyText_FalseEvaluation_Success() {
        // Given
        String mission = "Calculate the square root of 16";
        String userAnswer = "The answer is 5";

        GPTResponseDTO expectedResponse = createMockGPTResponse("false");

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(expectedResponse);

        // When
        GPTResponseDTO result = gptService.requestOnlyText(mission, userAnswer);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getChoices().get(0).getMessage().getContent()).isEqualTo("false");

        // Verify the prompt contains both mission and user answer
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        String promptContent = capturedRequest.getMessages().get(0).getContent().toString();
        assertThat(promptContent).contains("Calculate the square root of 16");
        assertThat(promptContent).contains("The answer is 5");
        assertThat(promptContent).contains("[true]");
        assertThat(promptContent).contains("[false]");
    }

    @Test
    @DisplayName("Image analysis - Complex mission")
    void requestImageAnalysis_ComplexMission_Success() {
        // Given
        String mission = "Verify that the user is performing a proper squat with knees behind toes and back straight";
        String imageUrl = "https://example.com/squat-form.jpg";

        GPTResponseDTO expectedResponse = createMockGPTResponse("true");

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(expectedResponse);

        // When
        GPTResponseDTO result = gptService.requestImageAnalysis(mission, imageUrl);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getChoices().get(0).getMessage().getContent()).isEqualTo("true");

        // Verify request structure for image analysis
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        assertThat(capturedRequest.getMaxTokens()).isEqualTo(500); // Image analysis uses 500 tokens
        
        MessageDTO message = capturedRequest.getMessages().get(0);
        assertThat(message.getRole()).isEqualTo("user");
        assertThat(message.getContent()).isInstanceOf(List.class);
    }

    @Test
    @DisplayName("Counseling request - Remaining chats calculation")
    void requestCounseling_RemainingChatsCalculation_Success() {
        // Given
        String userId = "user789";
        String question = "How should I allocate my retirement savings?";
        boolean isAsset = false;

        String gptAnswer = "Consider a mix of 401k, IRA, and taxable investments based on your age and risk tolerance.";
        GPTResponseDTO gptResponse = createMockGPTResponse(gptAnswer);
        int remainingChats = 2; // Low remaining chats

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(gptResponse);
        given(aiChatService.saveAiChatMessage(userId, question, "user")).willReturn(0);
        given(aiChatService.saveAiChatMessage(userId, gptAnswer, "assistant")).willReturn(remainingChats);

        // When
        GPTResponseDTO result = gptService.requestCounseling(userId, question, isAsset);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getRemainingChats()).isEqualTo(remainingChats);
        
        // Verify the response was modified with remaining chats
        then(aiChatService).should().saveAiChatMessage(userId, question, "user");
        then(aiChatService).should().saveAiChatMessage(userId, gptAnswer, "assistant");
    }

    @Test
    @DisplayName("Request parameters validation")
    void requestValidation_CorrectParameters_Success() {
        // Given
        String mission = "Test mission";
        String userAnswer = "Test answer";

        GPTResponseDTO expectedResponse = createMockGPTResponse("true");

        given(restTemplate.postForObject(eq(apiUrl), any(GPTRequestDTO.class), eq(GPTResponseDTO.class)))
                .willReturn(expectedResponse);

        // When
        gptService.requestOnlyText(mission, userAnswer);

        // Then
        ArgumentCaptor<GPTRequestDTO> requestCaptor = ArgumentCaptor.forClass(GPTRequestDTO.class);
        then(restTemplate).should().postForObject(eq(apiUrl), requestCaptor.capture(), eq(GPTResponseDTO.class));

        GPTRequestDTO capturedRequest = requestCaptor.getValue();
        
        // Verify all request parameters
        assertThat(capturedRequest.getModel()).isEqualTo("gpt-4");
        assertThat(capturedRequest.getMaxTokens()).isEqualTo(300);
        assertThat(capturedRequest.getMessages()).hasSize(1);
        assertThat(capturedRequest.getMessages().get(0).getRole()).isEqualTo("user");
        assertThat(capturedRequest.getMessages().get(0).getContent()).isNotNull();
    }

    // Helper method to create mock GPT response
    private GPTResponseDTO createMockGPTResponse(String content) {
        MessageDTO responseMessage = MessageDTO.createOnlyText("assistant", content);
        ChoiceDTO choice = new ChoiceDTO(0, responseMessage);
        
        return GPTResponseDTO.builder()
                .id("test-id")
                .choices(Arrays.asList(choice))
                .build();
    }
}
