package com.goormthon.whattoeat.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String memberId;

    private int amount;
    private String category;
    private LocalDate date;
    private String title;
    private String memo;

    @Builder
    public Expense(String memberId, int amount, String category, LocalDate date, String title, String memo) {
        this.memberId = memberId;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.title = title;
        this.memo = memo;
    }
}
