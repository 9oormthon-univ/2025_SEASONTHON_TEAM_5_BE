package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.request.UpdateBudgetRequest;
import com.goormthon.whattoeat.controller.dto.response.RemainingBudgetResponse;
import com.goormthon.whattoeat.controller.dto.response.UsedBudgetResponse;
import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.domain.Expense;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.repository.BudgetRepository;
import com.goormthon.whattoeat.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    public void createBudget(Member member, CreateBudgetRequest budgetRequest) {
        budgetRepository.save(Budget.builder()
                .member(member)
                .amount(budgetRequest.amount())
                .startDate(budgetRequest.startAt())
                .endDate(budgetRequest.endAt())
                .build());
    }

    public void updateBudget(long budgetId, UpdateBudgetRequest budgetRequest) {
        budgetRepository.findById(budgetId).ifPresent(budget -> {budget.from(budgetRequest);});
    }

    public RemainingBudgetResponse getRemainingBudgets(Member member, long budgetId) {
        Budget findBudget = budgetRepository.findById(budgetId)
                .orElseThrow();
        int totalExpense = expenseRepository.findByMemberAndDateBetween(member, findBudget.getStartDate(), findBudget.getEndDate()).stream()
                .mapToInt(Expense::getAmount)
                .sum();
        return RemainingBudgetResponse.builder()
                .totalBudget(findBudget.getAmount())
                .remainingBudget(findBudget.getAmount() - totalExpense)
                .build();
    }

    public UsedBudgetResponse getUsedBudgets(Member member, long budgetId) {
        Budget findBudget = budgetRepository.findById(budgetId)
                .orElseThrow();
        int totalExpense = expenseRepository.findByMemberAndDateBetween(member, findBudget.getStartDate(), findBudget.getEndDate()).stream()
                .mapToInt(Expense::getAmount)
                .sum();
        return UsedBudgetResponse.builder()
                .totalBudget(findBudget.getAmount())
                .usedBudget(totalExpense)
                .build();
    }
}
