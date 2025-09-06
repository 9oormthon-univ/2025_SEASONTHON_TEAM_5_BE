package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.controller.dto.request.CreateExpenseRequest;
import com.goormthon.whattoeat.controller.dto.response.RecentExpenseResponse;
import com.goormthon.whattoeat.domain.Expense;
import com.goormthon.whattoeat.domain.Member;
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

    public void createExpense(Member member, CreateExpenseRequest expenseRequest) {
        expenseRepository.save(Expense.builder()
                .member(member)
                .amount(expenseRequest.amount())
                .category(expenseRequest.category())
                .date(expenseRequest.date())
                .title(expenseRequest.title())
                .memo(expenseRequest.memo())
                .build());
    }

    public List<RecentExpenseResponse> recentExpense(Member member) {
        return expenseRepository.findByMemberOrderByDateDesc(member).stream()
                .map(expense -> RecentExpenseResponse.builder()
                        .title(expense.getTitle())
                        .category(expense.getCategory())
                        .amount(expense.getAmount())
                        .build())
                .limit(5)
                .toList();
    }
}
