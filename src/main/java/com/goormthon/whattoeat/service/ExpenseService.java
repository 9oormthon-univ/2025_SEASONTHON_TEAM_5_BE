package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.controller.dto.response.RecentExpenseResponse;
import com.goormthon.whattoeat.domain.Expense;
import com.goormthon.whattoeat.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public void createExpense(String tempUserId, CreateExpenseRequest expenseRequest) {
        expenseRepository.save(Expense.builder()
                .memberId(tempUserId)
                .amount(expenseRequest.amount())
                .category(expenseRequest.category())
                .date(expenseRequest.date())
                .title(expenseRequest.title())
                .memo(expenseRequest.memo())
                .build());
    }

    public List<RecentExpenseResponse> recentExpense(String tempUserId) {
        return expenseRepository.findByMemberIdOrderByDateDesc(tempUserId).stream()
                .map(expense -> RecentExpenseResponse.builder()
                        .title(expense.getTitle())
                        .category(expense.getCategory())
                        .amount(expense.getAmount())
                        .build())
                .limit(5)
                .toList();
    }
}
