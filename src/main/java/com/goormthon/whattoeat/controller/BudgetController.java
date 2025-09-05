package com.goormthon.whattoeat.controller;

import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.request.UpdateBudgetRequest;
import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.service.BudgetService;
import com.goormthon.whattoeat.tempUser.annotation.TempUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{budgetId}")
    public ResponseEntity<Void> updateBudget(@PathVariable long budgetId, @RequestBody UpdateBudgetRequest budgetRequest) {
        budgetService.updateBudget(budgetId, budgetRequest);
        return ResponseEntity.ok().build();
    }
}
