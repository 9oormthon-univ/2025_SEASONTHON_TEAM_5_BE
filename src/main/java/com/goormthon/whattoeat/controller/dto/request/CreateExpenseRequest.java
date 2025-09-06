package com.goormthon.whattoeat.controller.dto.request;

import java.time.LocalDate;

public record CreateExpenseRequest(
        int amount,
        String category,
        LocalDate date,
        String title,
        String memo
) {
}
