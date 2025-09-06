package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Expense;
import com.goormthon.whattoeat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByMemberOrderByDateDesc(Member member);
    List<Expense> findByMemberAndDateBetween(Member member, LocalDate dateAfter, LocalDate dateBefore);
}
