package com.kbulkup.user.mapper;

import com.kbulkup.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndLoginType(@Param("email") String email, @Param("loginType") String loginType);

    Optional<User> findByProviderIdAndLoginType(@Param("providerId") String providerId, @Param("loginType") String loginType);

    void saveUser(User user);

    void saveUserRole(@Param("userId") Long userId, @Param("role") String role);

    boolean existsUserRole(@Param("userId") Long userId, @Param("role") String role);

    void updateUserProviderId(@Param("userId") long userId, @Param("providerId") String providerId);

}
