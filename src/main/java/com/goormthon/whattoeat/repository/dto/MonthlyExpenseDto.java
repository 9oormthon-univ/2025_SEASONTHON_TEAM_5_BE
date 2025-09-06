package com.goormthon.whattoeat.repository.dto;

public record MonthlyExpenseDto(
        Integer year,
        Integer month,
        Long totalAmount
) {
}
