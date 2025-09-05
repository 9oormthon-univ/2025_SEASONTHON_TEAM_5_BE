package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.dto.RecipeRequest;
import com.goormthon.whattoeat.dto.RecipeResponse;
import com.goormthon.whattoeat.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> getRecipe(@RequestBody @Valid RecipeRequest request){
        return ResponseEntity.ok(recipeService.getRecipe(request));
    }
}
