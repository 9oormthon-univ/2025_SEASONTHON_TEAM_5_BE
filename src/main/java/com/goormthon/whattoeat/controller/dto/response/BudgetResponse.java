package com.goormthon.whattoeat.controller.dto.response;

import lombok.Builder;

@Builder
public record BudgetResponse(
        int remainingBudget,
        int totalBudget
) {
}
