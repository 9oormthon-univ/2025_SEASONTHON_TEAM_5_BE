package com.goormthon.whattoeat.controller.dto.response;

import lombok.Builder;

@Builder
public record RecentExpenseResponse(
        String title,
        String category,
        int amount
) {
}
