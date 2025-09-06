package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeDto;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.service.IngredientService;
import com.goormthon.whattoeat.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Tag(name = "레시피 관리", description = "레시피 생성 및 관리 API")
public class RecipeController {
    
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @PostMapping
    @Operation(summary = "레시피 생성", description = "사용자의 재료를 기반으로 레시피를 생성합니다.")
    public ResponseEntity<List<RecipeDto>> getRecipe(@AuthenticationPrincipal Member member,
                                                     @RequestBody @Valid RecipeRequest recipeRequest) {
        List<RecipeDto> recipeResponse = recipeService.getRecipe(member, recipeRequest);
        return ResponseEntity.ok(recipeResponse);
    }
}
