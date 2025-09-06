package com.goormthon.whattoeat.domain;

import com.goormthon.whattoeat.controller.dto.request.UpdateBudgetRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int amount;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Budget(Member member, int amount, LocalDate startDate, LocalDate endDate) {
        this.member = member;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void from(UpdateBudgetRequest budgetRequest) {
        this.amount = budgetRequest.amount();
        this.startDate = budgetRequest.startAt();
        this.endDate = budgetRequest.endAt();
    }
}
