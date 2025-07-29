package com.kbulkup.user.mapper;

import com.kbulkup.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {

    // email로 사용자 조회
    Optional<User> findByEmail(@Param("email") String email);

    // email과 로그인 타입으로 사용자 조회
    Optional<User> findByEmailAndLoginType(@Param("email") String email, @Param("loginType") String loginType);

    // 공급자 ID와 로그인 타입으로 사용자 조회
    Optional<User> findByProviderIdAndLoginType(@Param("providerId") String providerId, @Param("loginType") String loginType);

    // 사용자 ID로 사용자 조회 (AuthServiceImpl에서 필요)
    Optional<User> findById(Long userId);

    // 신규 사용자 정보 저장
    void saveUser(User user);

    // 사용자의 역할 저장 (userId와 role을 받아 join 테이블에 저장)
    void saveUserRole(@Param("userId") Long userId, @Param("role") String role);

    // 특정 역할이 존재하는지 확인
    boolean existsUserRole(@Param("userId") Long userId, @Param("role") String role);

    // 사용자 공급자 ID 업데이트
    void updateUserProviderId(@Param("userId") long userId, @Param("providerId") String providerId);
}