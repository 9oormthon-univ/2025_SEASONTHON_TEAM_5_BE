package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.service.BudgetService;
import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<Void> createBudget(@TempUserId String tempUserId, @RequestBody CreateBudgetRequest budgetRequest) {
        budgetService.createBudget(tempUserId, budgetRequest);
        return ResponseEntity.ok().build();
    }
}
