package com.kbulkup.user.mapper;

import com.kbulkup.user.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndLoginType(@Param("email") String email, @Param("loginType") String loginType);

    void saveUser(User user);

    void saveUserRole(@Param("userId") long userId, @Param("role") String role);

}
