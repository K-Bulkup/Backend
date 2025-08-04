package com.kbulkup.common.util;

public class PromptBuilder {

    public static String buildTextPrompt(String mission, String userAnswer) {
        return """
            당신은 주관식 미션을 채점하는 전문가입니다.
    
            아래는 사용자의 미션과 그에 대한 답변입니다.
            
            [미션]
            %s
            
            [사용자 답변]
            %s
            
            미션의 핵심 요구사항을 충족했다면 [true], 아니라면 [false]로만 응답하십시오.
            이유나 해설 없이 오직 [true] 또는 [false]만 출력하십시오.
            """.formatted(mission, userAnswer);
    }

    public static String buildImagePrompt(String mission) {
        return """
            당신은 이미지 채점 전문가입니다.
    
            아래 미션을 읽고, 첨부된 이미지를 분석하여 사용자가 미션을 수행했는지 평가하십시오.
    
            [미션]
            %s
    
            이미지를 분석한 후, 미션을 제대로 수행한 것이 명확하다면 [true], 그렇지 않다면 [false]로만 응답하십시오.
            이유나 해설은 생략하고 반드시 [true] 또는 [false]만 출력하십시오.
            """.formatted(mission);
    }

    public static String buildAssetConsultingPrompt(String userQuestion) {
        return """
            당신은 금융 전문가이자 친절한 자산 컨설턴트입니다.

            사용자의 질문에 대해 구체적이고 실용적인 금융 조언을 제공해야 합니다.
            전문용어를 피하고, 누구나 이해할 수 있도록 간결하고 따뜻하게 설명해 주세요.
            설명은 너무 길지 않고, 간결하게 3줄 이하로 작성해줘.

            [사용자 질문]
            %s
            """.formatted(userQuestion);
    }
}
