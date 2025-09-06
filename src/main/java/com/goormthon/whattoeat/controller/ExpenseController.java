package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.service.ExpenseService;
import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Void> createExpense(@TempUserId String tempUserId, @RequestBody CreateExpenseRequest expenseRequest) {
        expenseService.createExpense(tempUserId, expenseRequest);
        return ResponseEntity.ok().build();
    }
}
