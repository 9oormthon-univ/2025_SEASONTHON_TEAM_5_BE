// service/AuthService.java
package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.AuthDtos.*;
import com.goormthon.whattoeat.repository.MemberRepository;
import com.goormthon.whattoeat.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwt;

    @Transactional
    public void register(RegisterRequest req) {
        if (memberRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        String hash = encoder.encode(req.getPassword());
        memberRepository.save(new Member(req.getEmail(), hash));
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!encoder.matches(req.getPassword(), member.getPasswordHash())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return jwt.generateToken(member.getId(), member.getEmail(), member.getRole());
    }
}