package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.IngredientListResponse;
import com.goormthon.whattoeat.repository.IngredientRepository;
import com.goormthon.whattoeat.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    public IngredientController(IngredientRepository ingredientRepository, IngredientService ingredientService){
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<IngredientListResponse>> getIngredientList(int memberId){
        List<IngredientListResponse> ingredientListResponses = ingredientService.getIngredientList(memberId);
        return ResponseEntity.ok(ingredientListResponses);
    }

}
