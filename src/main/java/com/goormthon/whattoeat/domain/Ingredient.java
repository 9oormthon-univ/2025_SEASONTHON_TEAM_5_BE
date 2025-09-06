package com.goormthon.whattoeat.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {
    @Id @Column(name = "ingredient_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String ingredientName;
    private int quantity;
    private String unit;
    private Date expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void updateQuantiy(int newQuantity){
        if (newQuantity>=0) {
            this.quantity = newQuantity;
        }
        else{
            this.quantity = 0;
        }
    }

    public void update(String newName, int newQuantity, String newUnit, Date newExpirationDate){
        this.ingredientName = newName;
        this.quantity = newQuantity;
        this.unit = newUnit;
        this.expirationDate = newExpirationDate;
    }

    public Ingredient(String ingredientName, int quantity, String unit, Date expirationDate){
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationDate = expirationDate;
    }
}