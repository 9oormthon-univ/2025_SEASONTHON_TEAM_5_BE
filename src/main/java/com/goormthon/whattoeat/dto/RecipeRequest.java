package com.goormthon.whattoeat.dto;

import com.goormthon.whattoeat.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private List<Ingredient> ingredientList;
    private Integer cookingTime;
}
