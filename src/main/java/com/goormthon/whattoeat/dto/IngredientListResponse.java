package com.goormthon.whattoeat.dto;

import com.goormthon.whattoeat.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IngredientListResponse {
    private Long id;
    private String name;
    private int quantity;
    private String unit;
    private Date exprirationDate;
}
