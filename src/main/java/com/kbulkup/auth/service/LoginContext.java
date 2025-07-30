package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;
import com.kbulkup.common.exception.AuthException;
import com.kbulkup.common.response.ResponseCode;

import com.kbulkup.auth.service.LoginStrategy;
import com.kbulkup.auth.service.KakaoLoginStrategy;
import com.kbulkup.auth.service.LocalLoginStrategy;
import com.kbulkup.auth.service.NaverLoginStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class LoginContext {
    private final Map<String, LoginStrategy> loginStrategies;

    public LoginContext(ApplicationContext applicationContext) {
        Map<String, LoginStrategy> strategyBeans = applicationContext.getBeansOfType(LoginStrategy.class);
        this.loginStrategies = strategyBeans.values().stream()
                .collect(Collectors.toMap(LoginStrategy::getLoginType, Function.identity()));
    }

    public LoginResponseDTO executeLogin(LoginRequestDTO dto) {
        LoginStrategy strategy = loginStrategies.get(dto.getLoginType());
        if (strategy == null) {
            throw new AuthException(ResponseCode.AUTH_UNSUPPORTED_LOGIN_TYPE);
        }
        return strategy.login(dto);
    }

    public LoginResponseDTO executeSocialLogin(String loginType, String code) {
        LoginStrategy strategy = loginStrategies.get(loginType);
        if (strategy == null) {
            throw new AuthException(ResponseCode.AUTH_UNSUPPORTED_LOGIN_TYPE);
        }
        return strategy.login(code);
    }
}
