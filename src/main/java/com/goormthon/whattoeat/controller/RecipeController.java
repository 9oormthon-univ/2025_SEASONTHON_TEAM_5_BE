package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.service.IngredientService;
import com.goormthon.whattoeat.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService){
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> getRecipe(@RequestBody @Valid RecipeRequest recipeRequest){
        RecipeResponse recipeResponse= recipeService.getRecipe(recipeRequest);
        ingredientService.consumeUsedIngredients(recipeResponse);
        return ResponseEntity.ok(recipeResponse);
    }
}
