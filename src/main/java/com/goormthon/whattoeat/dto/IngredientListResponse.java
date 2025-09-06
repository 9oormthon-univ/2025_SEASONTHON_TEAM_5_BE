package com.goormthon.whattoeat.dto;

import com.goormthon.whattoeat.domain.Ingredient;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class IngredientListResponse {
    private Long id;
    private String name;
    private int quantity;
    private String unit;
    private Date exprirationDate;
}
