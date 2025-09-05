package com.goormthon.whattoeat.domain;

import jakarta.persistence.*;
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
}