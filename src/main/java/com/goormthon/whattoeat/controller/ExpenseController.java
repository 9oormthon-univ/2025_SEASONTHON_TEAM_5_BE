package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.controller.dto.response.RecentExpenseResponse;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
@Tag(name = "지출", description = "지출 API")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @Operation(summary = "지출 생성", description = "특정일의 지출을 생성합니다")
    @ApiResponse(responseCode = "200", description = "지출 생성 성공")
    public ResponseEntity<Void> createExpense(@AuthenticationPrincipal Member member, @RequestBody CreateExpenseRequest expenseRequest) {
        expenseService.createExpense(member, expenseRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recents")
    @Operation(summary = "최근 지출 5개 조회", description = "최근 지출을 최대 5개 반환합니다")
    @ApiResponse(responseCode = "200", description = "지출 조회 성공")
    public ResponseEntity<List<RecentExpenseResponse>> recentExpense(@AuthenticationPrincipal Member member) {
        List<RecentExpenseResponse> expenses = expenseService.recentExpense(member.getId().toString());
        return ResponseEntity.ok(expenses);
    }
}
