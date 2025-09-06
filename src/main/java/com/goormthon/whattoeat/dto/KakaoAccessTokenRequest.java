package com.goormthon.whattoeat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAccessTokenRequest {
    private String accessToken;
}
