package com.goormthon.whattoeat.controller.dto.request;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record UpdateBudgetRequest(
        @Min(0)
        int amount,
        LocalDate startAt,
        LocalDate endAt
) {
}
