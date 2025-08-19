package com.kbulkup.profile.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(description = "트레이너 프로필 이미지 수정 요청(멀티파트)")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileImageUpdateRequestDTO {
    @ApiModelProperty(value = "프로필 이미지 파일")
    private MultipartFile profileImage;
}
