package com.kbulkup.gpt.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore // 내부용
@Getter
@SuperBuilder
@NoArgsConstructor
public class ImageContentDTO extends ContentDTO {
    private ImageUrl image_url;

    public ImageContentDTO(String imageUrl) {
        super("image_url");
        this.image_url = ImageUrl.create(imageUrl);
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageUrl {
        private String url;

        public static ImageUrl create(String url) {
            return ImageUrl.builder().url(url).build();
        }
    }
}
