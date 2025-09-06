package com.goormthon.whattoeat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String KAKAO_USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    /**
     * 카카오 액세스 토큰으로 사용자 정보 조회
     */
    public Map<String, Object> getUserInfo(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_USER_INFO_URL,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return objectMapper.readValue(response.getBody(), Map.class);
            } else {
                log.error("카카오 API 호출 실패: {}", response.getStatusCode());
                throw new RuntimeException("카카오 사용자 정보 조회 실패");
            }
        } catch (Exception e) {
            log.error("카카오 API 호출 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("카카오 사용자 정보 조회 중 오류 발생", e);
        }
    }
}
