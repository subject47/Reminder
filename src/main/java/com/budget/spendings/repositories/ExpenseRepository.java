package com.budget.spendings.repositories;

import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.budget.spendings.domain.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {

  List<Expense> findByDateBetween(Date startDate, Date endDate);
}
