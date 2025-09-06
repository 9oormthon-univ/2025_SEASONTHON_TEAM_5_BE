package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.request.UpdateBudgetRequest;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
@Tag(name = "예산", description = "예산 API")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "예산 생성", description = "특정 기간의 예산을 생성합니다")
    @ApiResponse(responseCode = "200", description = "예산 생성 성공")
    public ResponseEntity<Void> createBudget(@AuthenticationPrincipal Member member, @RequestBody CreateBudgetRequest budgetRequest) {
        budgetService.createBudget(member, budgetRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{budgetId}")
    @Operation(summary = "예산 수정", description = "예산 ID로 예산을 수정합니다")
    @ApiResponse(responseCode = "200", description = "예산 수정 성공")
    public ResponseEntity<Void> updateBudget(@PathVariable long budgetId, @RequestBody UpdateBudgetRequest budgetRequest) {
        budgetService.updateBudget(budgetId, budgetRequest);
        return ResponseEntity.ok().build();
    }
}
