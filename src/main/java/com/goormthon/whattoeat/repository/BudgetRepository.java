package com.goormthon.whattoeat.repository;

import com.goormthon.whattoeat.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
}
