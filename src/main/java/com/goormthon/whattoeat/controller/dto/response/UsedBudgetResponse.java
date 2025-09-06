package com.goormthon.whattoeat.controller.dto.response;

import lombok.Builder;

@Builder
public record UsedBudgetResponse(
        int usedBudget,
        int totalBudget
) {
}
