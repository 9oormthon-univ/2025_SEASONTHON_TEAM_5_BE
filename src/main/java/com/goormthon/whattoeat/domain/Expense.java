package com.goormthon.whattoeat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    private Budget budget;

    private int amount;
    private String category;
    private LocalDate date;
    private String title;
    private String memo;

    @Builder
    public Expense(Member member, Budget budget, int amount, String category, LocalDate date, String title, String memo) {
        this.member = member;
        this.budget = budget;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.title = title;
        this.memo = memo;
    }
}
