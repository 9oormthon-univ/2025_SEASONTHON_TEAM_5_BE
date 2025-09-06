package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Ingredient;
import com.goormthon.whattoeat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
//    List<Ingredient> findByMember_Id(Long memberId);

    Ingredient findByMemberAndIngredientName(Member member, String ingredientName);

    List<Ingredient> findByMemberOrderByExpirationDateAscIngredientNameAsc(Member member);
}