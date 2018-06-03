package com.example.reminder.services;

import java.time.Month;
import java.time.Year;
import java.util.List;

import com.example.reminder.domain.Expense;

public interface ExpenseService extends CRUDService<Expense> {
	List<Expense> findAllExpensesForYearAndMonth(Year year, Month month);
}
