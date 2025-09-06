package com.goormthon.whattoeat.controller.dto.response;

import lombok.Builder;

@Builder
public record ThisMonthBudget(
        long id,
        int amount
) {
}
