package com.kbulkup.payment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthUserMapper {
    Long findUserIdByEmail(@Param("email") String email);
}
