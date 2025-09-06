package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.dto.IngredientCreateRequest;
import com.goormthon.whattoeat.dto.IngredientListResponse;
import com.goormthon.whattoeat.dto.IngredientUpdateRequest;
import com.goormthon.whattoeat.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
@RequiredArgsConstructor
@Tag(name = "재료 관리", description = "재료 CRUD API")
public class IngredientController {
    
    private final IngredientService ingredientService;

    @GetMapping
    @Operation(summary = "재료 목록 조회", description = "사용자의 재료 목록을 조회합니다.")
    public ResponseEntity<List<IngredientListResponse>> getIngredientList(@AuthenticationPrincipal Member member) {
        List<IngredientListResponse> ingredientListResponses = ingredientService.getIngredientList(member.getId());
        return ResponseEntity.ok(ingredientListResponses);
    }

    @PostMapping
    @Operation(summary = "재료 생성", description = "새로운 재료를 추가합니다.")
    @ApiResponse(responseCode = "200", description = "재료 생성 성공")
    public ResponseEntity<Void> createIngredient(@AuthenticationPrincipal Member member, 
                                                @RequestBody IngredientCreateRequest ingredientCreateRequest) {
        ingredientService.createIngredient(member.getId(), ingredientCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "재료 수정", description = "기존 재료 정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "재료 수정 성공")
    public ResponseEntity<Void> updateIngredient(@RequestBody IngredientUpdateRequest ingredientUpdateRequest) {
        ingredientService.updateIngredient(ingredientUpdateRequest);
        return ResponseEntity.ok().build();
    }
}
