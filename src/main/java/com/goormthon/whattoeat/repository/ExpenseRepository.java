package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByMemberIdOrderByDateDesc(String memberId);
    List<Expense> findByMemberIdAndDateBetween(Long memberId, LocalDate dateAfter, LocalDate dateBefore);
}
