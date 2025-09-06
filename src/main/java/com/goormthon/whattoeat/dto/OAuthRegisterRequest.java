package com.goormthon.whattoeat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterRequest {
    private String email;
    private String provider;
    private String nickname;
}
