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
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memberId;
    private int amount;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public Budget(String memberId, int amount, LocalDate startDate, LocalDate endDate) {
        this.memberId = memberId;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
