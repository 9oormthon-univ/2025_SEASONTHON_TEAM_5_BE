package com.goormthon.whattoeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private String name;
    private String quantity;
    private String unit;
}