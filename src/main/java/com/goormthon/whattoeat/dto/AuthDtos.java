// dto/AuthDtos.java
package com.goormthon.whattoeat.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDtos {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class TokenResponse {
        private String accessToken;
    }
}