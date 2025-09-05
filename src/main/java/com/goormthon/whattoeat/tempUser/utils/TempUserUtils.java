package com.goormthon.whattoeat.tempUser.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TempUserUtils {

    private static final String TEMP_USER_SESSION_KEY = "tempUserId";
    private static final String TEMP_USER_REQUEST_KEY = "currentTempUserId";
    private static final String TEMP_USER_PREFIX = "temp_";

    /**
     * 현재 요청의 임시 사용자 ID 조회
     */
    public String getCurrentTempUserId(HttpServletRequest request) {
        String tempUserId = (String) request.getAttribute(TEMP_USER_REQUEST_KEY);
        if (tempUserId == null) {
            throw new IllegalStateException("임시 사용자 ID가 설정되지 않았습니다. Interceptor 설정을 확인해주세요.");
        }
        return tempUserId;
    }

    /**
     * 세션에서 임시 사용자 ID 조회 또는 생성
     */
    public String getOrCreateTempUserId(HttpSession session) {
        String tempUserId = (String) session.getAttribute(TEMP_USER_SESSION_KEY);
        if (tempUserId == null) {
            tempUserId = generateTempUserId();
            session.setAttribute(TEMP_USER_SESSION_KEY, tempUserId);
        }
        return tempUserId;
    }

    /**
     * 새로운 임시 사용자 ID 생성
     */
    public String generateTempUserId() {
        return TEMP_USER_PREFIX + System.currentTimeMillis() + "_" +
                UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    /**
     * 임시 사용자 ID인지 확인
     */
    public boolean isTempUserId(String userId) {
        return userId != null && userId.startsWith(TEMP_USER_PREFIX);
    }

    /**
     * 현재 세션의 임시 사용자 ID 초기화 (강제 재생성용)
     */
    public String resetTempUserId(HttpSession session) {
        session.removeAttribute(TEMP_USER_SESSION_KEY);
        return getOrCreateTempUserId(session);
    }

    /**
     * 세션에 임시 사용자 ID 설정 (Interceptor용)
     */
    public void setCurrentTempUserId(HttpServletRequest request, String tempUserId) {
        request.setAttribute(TEMP_USER_REQUEST_KEY, tempUserId);
    }
}
