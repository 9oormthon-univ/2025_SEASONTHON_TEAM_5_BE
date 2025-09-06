package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.jwt.JwtTokenProvider;
import com.goormthon.whattoeat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    /**
     * Access Token과 Refresh Token을 생성하여 반환
     */
    @Transactional
    public Map<String, String> generateTokens(Member member) {
        String accessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // Refresh Token을 데이터베이스에 저장 (선택사항)
        member.setRefreshToken(refreshToken);
        memberRepository.save(member);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        tokens.put("tokenType", "Bearer");

        return tokens;
    }

    /**
     * Refresh Token을 사용하여 새로운 Access Token 발급
     */
    @Transactional
    public Map<String, String> refreshAccessToken(String refreshToken) {
        // Refresh Token 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        if (!"refresh".equals(jwtTokenProvider.getTokenType(refreshToken))) {
            throw new IllegalArgumentException("Invalid token type");
        }

        // 사용자 ID 추출
        Long userId = jwtTokenProvider.getUserIdFromToken(refreshToken);

        // 사용자 정보 조회
        Optional<Member> memberOpt = memberRepository.findById(userId);
        if (memberOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        Member member = memberOpt.get();

        // 저장된 Refresh Token과 일치하는지 확인
        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh token mismatch");
        }

        // 새로운 Access Token 생성
        String newAccessToken = jwtTokenProvider.createAccessToken(member.getId(), member.getEmail());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("tokenType", "Bearer");

        return tokens;
    }

    /**
     * 토큰에서 사용자 정보 추출
     */
    public Member getUserFromToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid token");
        }

        Long userId = jwtTokenProvider.getUserIdFromToken(token);
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    /**
     * 로그아웃 시 Refresh Token 무효화
     */
    @Transactional
    public void invalidateRefreshToken(Long userId) {
        Optional<Member> memberOpt = memberRepository.findById(userId);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setRefreshToken(null);
            memberRepository.save(member);
        }
    }

    /**
     * 토큰 유효성 검증
     */
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
