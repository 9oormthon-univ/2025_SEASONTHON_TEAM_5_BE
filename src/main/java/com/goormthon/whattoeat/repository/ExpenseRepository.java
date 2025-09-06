package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
