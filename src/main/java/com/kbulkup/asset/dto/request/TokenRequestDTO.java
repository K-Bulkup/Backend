package com.kbulkup.asset.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDTO {
    private String bank;

    public static TokenRequestDTO create(String bank) {
        return TokenRequestDTO.builder()
                .bank(bank)
                .build();
    }
}
