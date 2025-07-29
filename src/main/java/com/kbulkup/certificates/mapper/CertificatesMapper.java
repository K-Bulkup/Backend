package com.kbulkup.certificates.mapper;

import com.kbulkup.certificates.dto.request.CertificateCreateRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface CertificatesMapper {

    void createTrainerCertification(CertificateCreateRequestDTO dto);
    List<String> findByTrainerId(@PathVariable Long trainerId);

}
