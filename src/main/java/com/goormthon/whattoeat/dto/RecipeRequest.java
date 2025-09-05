package com.goormthon.whattoeat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
//    private Integer userId;               // DB에서 재료 조회할 사용자 ID
                                            // 이후 로그인 구현 시 삭제
    @JsonProperty("cook_time_minutes")
    private Integer cookTimeMinutes;      // 조리 시간(분)
}