package com.kbulkup.routine.domain;

import lombok.Getter;

@Getter
public class RoutineAnswer {
    private Long routineId;
    private String answer;

    public static RoutineAnswer of(Long routineId, String answer) {
        RoutineAnswer routineAnswer = new RoutineAnswer();
        routineAnswer.routineId = routineId;
        routineAnswer.answer = answer;
        return routineAnswer;
    }
}
