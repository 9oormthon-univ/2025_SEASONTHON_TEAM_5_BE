package com.goormthon.whattoeat.controller.dto.response;

import lombok.Builder;

@Builder
public record ExpenseAnalyzeResponse(
    int expense
) {
}
