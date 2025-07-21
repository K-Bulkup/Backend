package com.kbulkup.auth.service;

import org.springframework.stereotype.Service;

/**
 * 로그아웃 비즈니스 로직을 처리하는 서비스
 */
@Service
public class LogoutService {

    /**
     * 로그아웃을 수행합니다.
     * @return 성공 메시지
     */
    public String performLogout() {
        // 현재 JWT(stateless) 방식에서는 서버에서 특별히 처리할 작업은 없습니다.
        // 클라이언트 측에서 토큰을 삭제하는 것이 핵심입니다.
        // 추후 토큰 블랙리스트와 같은 stateful 로직이 필요할 경우 여기에 구현합니다.
        return "성공적으로 로그아웃되었습니다.";
    }
}
