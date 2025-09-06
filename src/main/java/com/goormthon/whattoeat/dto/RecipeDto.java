package com.goormthon.whattoeat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private String recipe_name;
    private List<RecipeIngredientDto> ingredients;
    private List<String> steps;
    private String description;
    private int cook_time_minutes;
}