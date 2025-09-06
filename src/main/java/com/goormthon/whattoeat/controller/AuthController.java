// controller/AuthController.java
package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.dto.AuthDtos.*;
import com.goormthon.whattoeat.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest req) {
        String token = authService.login(req);
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT는 stateless → 로그아웃은 클라이언트가 토큰 삭제
        return ResponseEntity.ok().build();
    }
}