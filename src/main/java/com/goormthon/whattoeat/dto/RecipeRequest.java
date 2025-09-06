package com.goormthon.whattoeat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    @JsonProperty("cook_time_minutes")
    private Integer cookTimeMinutes;      // 조리 시간(분)
}