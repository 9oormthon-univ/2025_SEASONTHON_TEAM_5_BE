package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.service.ExpenseService;
import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
