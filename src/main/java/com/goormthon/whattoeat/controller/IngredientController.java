package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.IngredientCreateRequest;
import com.goormthon.whattoeat.dto.IngredientListResponse;
import com.goormthon.whattoeat.dto.IngredientUpdateRequest;
import com.goormthon.whattoeat.repository.IngredientRepository;
import com.goormthon.whattoeat.service.IngredientService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ApiResponse(responseCode = "200", description = "?")
    public ResponseEntity<Void> createIngredient(IngredientCreateRequest ingredientCreateRequest){
        int memberId = 1;
        ingredientService.createIngredient(memberId, ingredientCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @ApiResponse(responseCode = "200", description = "?")
    public ResponseEntity<Void> updateIngredient(IngredientUpdateRequest ingredientUpdateRequest){
        ingredientService.updateIngredient(ingredientUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
