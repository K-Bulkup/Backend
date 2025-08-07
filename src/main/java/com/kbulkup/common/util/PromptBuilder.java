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
    
            사용자의 질문이 자산(예: 예금, 투자, 펀드, 주식, 보험, 연금 등)과 관련이 있다면,
            구체적이고 실용적인 금융 조언을 3줄 이내로 간결하고 따뜻하게 설명해 주세요.
            단, 질문이 자산과 관련이 없다면 답변하지 말고 다음 문구를 출력하세요:
    
            자산과 관련된 질문을 해주세요. 예: 투자 방법, 자산 배분, 재무 계획 등
    
            또한, 사용자가 질문 대신 자산 관련 JSON 데이터를 전달한 경우
            해당 데이터를 바탕으로 소비 습관, 자산 구성, 자산 변화 흐름을 분석하여
            실용적인 자산 컨설팅을 3줄 이내로 제공해 주세요.
    
            [사용자 질문]
            %s
            """.formatted(userQuestion);
    }
}
