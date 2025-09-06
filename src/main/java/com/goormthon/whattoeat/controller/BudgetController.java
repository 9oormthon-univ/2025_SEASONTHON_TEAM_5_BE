package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.response.RemainingBudgetResponse;
import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.request.UpdateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.response.ThisMonthBudget;
import com.goormthon.whattoeat.controller.dto.response.UsedBudgetResponse;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.service.BudgetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/budgets")
@Tag(name = "예산", description = "예산 API")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    @Operation(summary = "예산 생성", description = "특정 기간의 예산을 생성합니다")
    @ApiResponse(responseCode = "201", description = "예산 생성 성공")
    public ResponseEntity<Void> createBudget(@AuthenticationPrincipal Member member, @RequestBody CreateBudgetRequest budgetRequest) {
        budgetService.createBudget(member, budgetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{budgetId}")
    @Operation(summary = "예산 수정", description = "예산 ID로 예산을 수정합니다")
    @ApiResponse(responseCode = "200", description = "예산 수정 성공")
    public ResponseEntity<Void> updateBudget(@PathVariable long budgetId, @RequestBody UpdateBudgetRequest budgetRequest) {
        budgetService.updateBudget(budgetId, budgetRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{budgetId}/remaining")
    @Operation(summary = "남은 예산 조회", description = "예산 ID로 남은 예산을 조회합니다")
    @ApiResponse(responseCode = "200", description = "예산 조회 성공")
    public ResponseEntity<RemainingBudgetResponse> getRemainingBudgets(@AuthenticationPrincipal Member member, @PathVariable long budgetId) {
        RemainingBudgetResponse result = budgetService.getRemainingBudgets(member, budgetId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{budgetId}/used")
    @Operation(summary = "기간별 사용한 예산 조회", description = "기간별로 사용한 예산을 조회합니다")
    @ApiResponse(responseCode = "200", description = "예산 조회 성공")
    public ResponseEntity<UsedBudgetResponse> getUsedBudgets(@AuthenticationPrincipal Member member, @PathVariable long budgetId) {
        UsedBudgetResponse result = budgetService.getUsedBudgets(member, budgetId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{date}")
    @Operation(summary = "이번달 예산 조회", description = "이번달 예산을 조회합니다")
    @ApiResponse(responseCode = "200", description = "예산 조회 성공")
    public ResponseEntity<ThisMonthBudget> getThisMonthBudget(LocalDate date, @AuthenticationPrincipal Member member) {
        ThisMonthBudget result = budgetService.getThisMonthBudget(date, member);
        return ResponseEntity.ok(result);
    }
}
