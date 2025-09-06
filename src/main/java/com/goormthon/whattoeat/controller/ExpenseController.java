package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.controller.dto.response.RecentExpenseResponse;
import com.goormthon.whattoeat.service.ExpenseService;
import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> createExpense(@TempUserId String tempUserId, @RequestBody CreateExpenseRequest expenseRequest) {
        expenseService.createExpense(tempUserId, expenseRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recents")
    @Operation(summary = "최근 지출 5개 조회", description = "최근 지출을 최대 5개 반환합니다")
    @ApiResponse(responseCode = "200", description = "지출 조회 성공")
    public ResponseEntity<List<RecentExpenseResponse>> recentExpense(@TempUserId String tempUserId) {
        List<RecentExpenseResponse> expenses = expenseService.recentExpense(tempUserId);
        return ResponseEntity.ok(expenses);
    }
}
