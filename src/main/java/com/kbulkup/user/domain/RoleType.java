package com.kbulkup.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum RoleType {
    TRAINER("TRAINER", "트레이너"),
    TRAINEE("TRAINEE", "훈련생");

    private final String key;
    private final String description;

    @JsonCreator
    public static RoleType fromKey(String key) {
        return Arrays.stream(RoleType.values())
                .filter(type -> type.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 역할 타입입니다: " + key));
    }
}
