package com.kbulkup.auth.service;

import com.kbulkup.auth.domain.LoginType;
import com.kbulkup.auth.dto.request.LoginRequestDTO;
import com.kbulkup.auth.dto.response.LoginResponseDTO;

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
        System.out.println("LoginContext: Found strategy beans: " + strategyBeans);
        this.loginStrategies = strategyBeans.values().stream()
                .collect(Collectors.toMap(LoginStrategy::getLoginType, Function.identity()));
        System.out.println("LoginContext: Initialized strategies map: " + this.loginStrategies);
    }

    public LoginResponseDTO executeLogin(LoginRequestDTO dto) {
        System.out.println("LoginContext received loginType for execution: " + dto.getLoginType());
        LoginStrategy strategy = loginStrategies.get(dto.getLoginType());
        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 로그인 방식입니다.");
        }
        return strategy.login(dto);
    }

    public LoginResponseDTO executeSocialLogin(String loginType, String code) {
        System.out.println("LoginContext received social loginType for execution: " + loginType);
        LoginStrategy strategy = loginStrategies.get(loginType);
        if (strategy == null) {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인 방식입니다.");
        }
        return strategy.login(code);
    }
}
