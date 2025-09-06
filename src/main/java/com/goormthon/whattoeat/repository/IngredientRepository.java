package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByMember_Id(Long memberId);

    Ingredient findByMember_IdAndIngredientName(Long memberId, String ingredientName);

    List<Ingredient> findByMember_IdOrderByExpirationDateAscIngredientNameAsc(Long memberId);
}