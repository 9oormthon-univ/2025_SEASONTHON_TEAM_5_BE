package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByMember_Id(Integer userId);
    Ingredient findByMember_IdAndIngredientName(int userId, String ingredientName);
}