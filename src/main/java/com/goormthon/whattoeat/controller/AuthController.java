package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.dto.*;
import com.goormthon.whattoeat.service.JwtService;
import com.goormthon.whattoeat.service.KakaoLoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/accounts/api")
@RequiredArgsConstructor
@Tag(name = "인증", description = "OAuth 로그인 및 JWT 토큰 관리 API")
public class AuthController {

    private final KakaoLoginService kakaoLoginService;
    private final JwtService jwtService;

    @PostMapping("/oauth-login")
    @Operation(summary = "OAuth 로그인", description = "이메일과 프로바이더로 로그인을 시도합니다.")
    public ResponseEntity<OAuthResponse> oauthLogin(@RequestBody OAuthLoginRequest request) {
        try {
            OAuthResponse response = kakaoLoginService.loginWithEmail(request.getEmail(), request.getProvider());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("OAuth 로그인 실패: {}", e.getMessage());
            return ResponseEntity.ok(OAuthResponse.builder()
                    .status("error")
                    .message("로그인 실패: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/oauth-register")
    @Operation(summary = "OAuth 회원가입", description = "이메일, 프로바이더, 닉네임으로 회원가입을 처리합니다.")
    public ResponseEntity<OAuthResponse> oauthRegister(@RequestBody OAuthRegisterRequest request) {
        try {
            OAuthResponse response = kakaoLoginService.register(request.getEmail(), request.getProvider(), request.getNickname());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("OAuth 회원가입 실패: {}", e.getMessage());
            return ResponseEntity.ok(OAuthResponse.builder()
                    .status("error")
                    .message("회원가입 실패: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/kakao/login")
    @Operation(summary = "카카오 액세스 토큰 로그인", description = "카카오 액세스 토큰으로 로그인을 처리합니다.")
    public ResponseEntity<OAuthResponse> kakaoLoginWithAccessToken(@RequestBody KakaoAccessTokenRequest request) {
        try {
            OAuthResponse response = kakaoLoginService.loginWithAccessToken(request.getAccessToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("카카오 로그인 실패: {}", e.getMessage());
            return ResponseEntity.ok(OAuthResponse.builder()
                    .status("error")
                    .message("로그인 실패: " + e.getMessage())
                    .build());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 갱신", description = "Refresh Token을 사용하여 새로운 Access Token을 발급합니다.")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        try {
            String refreshToken = request.get("refreshToken");
            Map<String, String> tokens = jwtService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            log.error("토큰 갱신 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자의 Refresh Token을 무효화합니다.")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        try {
            // Bearer 토큰에서 실제 토큰 추출
            String accessToken = token.replace("Bearer ", "");
            var member = jwtService.getUserFromToken(accessToken);
            jwtService.invalidateRefreshToken(member.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("로그아웃 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/validate")
    @Operation(summary = "토큰 검증", description = "Access Token의 유효성을 검증합니다.")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String accessToken = token.replace("Bearer ", "");
            boolean isValid = jwtService.validateToken(accessToken);
            
            Map<String, Object> response = Map.of(
                "valid", isValid,
                "message", isValid ? "유효한 토큰입니다." : "유효하지 않은 토큰입니다."
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
