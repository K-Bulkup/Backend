package com.kbulkup.training.dto.request;

import lombok.Getter;

@Getter
public class TrainingSearchListRequestDTO {
    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
