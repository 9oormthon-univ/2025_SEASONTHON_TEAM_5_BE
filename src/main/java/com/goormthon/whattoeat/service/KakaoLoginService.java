package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.OAuthResponse;
import com.goormthon.whattoeat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final KakaoApiService kakaoApiService;

    /**
     * 카카오 액세스 토큰으로 로그인 처리
     */
    @Transactional
    @SuppressWarnings("unchecked")
    public OAuthResponse loginWithAccessToken(String accessToken) {
        try {
            // 카카오 API로 사용자 정보 조회
            Map<String, Object> kakaoUserInfo = kakaoApiService.getUserInfo(accessToken);
            
            String kakaoId = String.valueOf(kakaoUserInfo.get("id"));
            Map<String, Object> kakaoAccount = (Map<String, Object>) kakaoUserInfo.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            
            String email = (String) kakaoAccount.get("email");
            String name = (String) profile.get("nickname");
            String profileImageUrl = (String) profile.get("profile_image_url");

            // 기존 사용자 조회
            Optional<Member> existingMember = memberRepository.findByKakaoId(kakaoId);
            
            Member member;
            if (existingMember.isPresent()) {
                // 기존 사용자 업데이트
                member = existingMember.get();
                member.setEmail(email);
                member.setName(name);
                member.setProfileImageUrl(profileImageUrl);
                member = memberRepository.save(member);
            } else {
                // 새 사용자 생성
                member = Member.builder()
                        .email(email)
                        .name(name)
                        .profileImageUrl(profileImageUrl)
                        .kakaoId(kakaoId)
                        .build();
                member = memberRepository.save(member);
            }
            
            // JWT 토큰 생성
            Map<String, String> tokens = jwtService.generateTokens(member);
            
            return OAuthResponse.builder()
                    .status("success")
                    .token(tokens.get("accessToken"))
                    .message("로그인 성공")
                    .build();
                    
        } catch (Exception e) {
            log.error("카카오 로그인 실패: {}", e.getMessage());
            return OAuthResponse.builder()
                    .status("error")
                    .message("로그인 실패: " + e.getMessage())
                    .build();
        }
    }

    /**
     * 이메일로 로그인 시도 (기존 사용자)
     */
    @Transactional
    public OAuthResponse loginWithEmail(String email, String provider) {
        try {
            Optional<Member> memberOpt = memberRepository.findByEmail(email);
            
            if (memberOpt.isPresent()) {
                Member member = memberOpt.get();
                Map<String, String> tokens = jwtService.generateTokens(member);
                
                return OAuthResponse.builder()
                        .status("success")
                        .token(tokens.get("accessToken"))
                        .message("로그인 성공")
                        .build();
            } else {
                return OAuthResponse.builder()
                        .status("register_required")
                        .message("회원가입이 필요합니다")
                        .build();
            }
        } catch (Exception e) {
            log.error("이메일 로그인 실패: {}", e.getMessage());
            return OAuthResponse.builder()
                    .status("error")
                    .message("로그인 실패: " + e.getMessage())
                    .build();
        }
    }

    /**
     * 회원가입 처리
     */
    @Transactional
    public OAuthResponse register(String email, String provider, String nickname) {
        try {
            // 이미 존재하는 사용자인지 확인
            if (memberRepository.findByEmail(email).isPresent()) {
                return OAuthResponse.builder()
                        .status("error")
                        .message("이미 존재하는 이메일입니다")
                        .build();
            }
            
            // 새 사용자 생성
            Member member = Member.builder()
                    .email(email)
                    .name(nickname)
                    .kakaoId(provider.equals("kakao") ? email : null) // 카카오가 아닌 경우 null
                    .build();
            
            member = memberRepository.save(member);
            Map<String, String> tokens = jwtService.generateTokens(member);
            
            return OAuthResponse.builder()
                    .status("success")
                    .token(tokens.get("accessToken"))
                    .message("회원가입 성공")
                    .build();
                    
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            return OAuthResponse.builder()
                    .status("error")
                    .message("회원가입 실패: " + e.getMessage())
                    .build();
        }
    }

    /**
     * 카카오 ID로 사용자 조회
     */
    public Optional<Member> findByKakaoId(String kakaoId) {
        return memberRepository.findByKakaoId(kakaoId);
    }

    /**
     * 이메일로 사용자 조회
     */
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
