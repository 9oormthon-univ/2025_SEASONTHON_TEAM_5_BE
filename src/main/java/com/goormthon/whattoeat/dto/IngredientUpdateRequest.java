package com.goormthon.whattoeat.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class IngredientUpdateRequest {
    Long id;
    private String name;
    private int quantity;
    private String unit;
    private Date exprirationDate;

}
