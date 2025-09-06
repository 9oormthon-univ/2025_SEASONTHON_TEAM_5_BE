package com.goormthon.whattoeat.dto;

import com.goormthon.whattoeat.domain.Ingredient;
import lombok.Getter;

import java.util.Date;

@Getter
public class IngredientCreateRequest {
    private String name;
    private int quantity;
    private String unit;
    private Date exprirationDate;

    public Ingredient toEntity(){
        return new Ingredient(name, quantity, unit, exprirationDate);
    }
}
