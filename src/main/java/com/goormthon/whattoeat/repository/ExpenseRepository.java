package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.domain.Expense;
import com.goormthon.whattoeat.domain.Member;
import com.goormthon.whattoeat.repository.dto.MonthlyExpenseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByMemberOrderByDateDesc(Member member);
    List<Expense> findByMemberAndDateBetween(Member member, LocalDate dateAfter, LocalDate dateBefore);

    @Query("SELECT new com.goormthon.whattoeat.repository.dto.MonthlyExpenseDto(YEAR(e.date), MONTH(e.date), SUM(e.amount)) " +
            "FROM Expense e " +
            "WHERE e.member = :member " +
            "AND e.date >= :sixMonthsAgo " +
            "GROUP BY YEAR(e.date), MONTH(e.date) " +
            "ORDER BY YEAR(e.date), MONTH(e.date)")
    List<MonthlyExpenseDto> findMonthlyExpense(@Param("member") Member member, @Param("sixMonthsAgo") LocalDate sixMonthsAgo);

}
