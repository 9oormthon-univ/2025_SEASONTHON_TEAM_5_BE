package com.goormthon.whattoeat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OAuthResponse {
    private String status;
    private String token; // Access Token
    private String refreshToken; // Refresh Token
    private String message;
}
