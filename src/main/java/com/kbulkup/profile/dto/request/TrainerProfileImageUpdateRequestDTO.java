package com.kbulkup.profile.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileImageUpdateRequestDTO {
    private MultipartFile profileImage;
}
