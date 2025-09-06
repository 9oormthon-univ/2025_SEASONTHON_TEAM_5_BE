package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Budget;
import com.goormthon.whattoeat.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("""
    SELECT b
    FROM Budget b
    WHERE b.member = :member
      AND :date BETWEEN b.startDate AND b.endDate
    """)
    Optional<Budget> findBudgetByDate(@Param("member") Member member, @Param("date") LocalDate date);
}
