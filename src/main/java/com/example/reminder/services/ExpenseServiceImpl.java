package com.example.reminder.services;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private ExpenseRepository expenseRepository;

  @Autowired
  public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  public List<Expense> listAll() {
    List<Expense> expenses = new ArrayList<>();
    expenseRepository.findAll().forEach(expenses::add);
    return expenses;
  }

  public Expense getById(Integer id) {
    return expenseRepository.findOne(id);
  }

  public Expense save(Expense expense) {
    return expenseRepository.save(expense);
  }

  public void delete(Integer id) {
    expenseRepository.delete(id);
  }

  @Override
  public List<Expense> findAllExpensesForYearAndMonth(Year year, Month month) {
    LocalDate start = LocalDate.of(year.getValue(), month, 1);
    LocalDate end = start.plusMonths(1).minusDays(1);
    return expenseRepository.findByDateBetween(DateUtils.asDate(start), DateUtils.asDate(end));
  }

}
