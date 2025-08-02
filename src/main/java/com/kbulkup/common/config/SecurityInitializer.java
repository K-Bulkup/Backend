package com.kbulkup.common.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    // 이 클래스는 Spring Security의 FilterChainProxy를 서블릿 컨테이너에 자동으로 등록합니다.
    // 별도의 코드 작성 없이 상속만으로 동작합니다.
}
