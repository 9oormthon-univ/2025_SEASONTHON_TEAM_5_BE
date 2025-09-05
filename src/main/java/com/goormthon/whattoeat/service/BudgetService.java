package com.goormthon.whattoeat.service;

import com.goormthon.whattoeat.controller.dto.request.CreateBudgetRequest;
import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    public void createBudget(String tempUserId, CreateBudgetRequest budgetRequest) {
        budgetRepository.save(Budget.builder()
                .memberId(tempUserId)
                .amount(budgetRequest.amount())
                .startDate(budgetRequest.startAt())
                .endDate(budgetRequest.endAt())
                .build());
    }
}
