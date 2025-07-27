package com.kbulkup.certificates.mapper;

import com.kbulkup.certificates.dto.request.CertificateCreateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CertificatesMapper {

    void createTrainerCertification(CertificateCreateRequestDTO dto);

}
