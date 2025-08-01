package com.kbulkup.common.security;

import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;
import com.kbulkup.user.mapper.UserMapper;
import com.kbulkup.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(email)
                .orElseThrow(() -> new AuthException(ResponseCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
