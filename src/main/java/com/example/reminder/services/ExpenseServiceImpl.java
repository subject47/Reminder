package com.example.reminder.services;

import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return expenseRepository.findById(id).get();
  }

  public Expense save(Expense expense) {
    return expenseRepository.save(expense);
  }

  public void delete(Integer id) {
    expenseRepository.deleteById(id);
  }

  @Override
  public List<Expense> findAllExpensesByYearAndMonth(Year year, Month month) {
    LocalDate start = LocalDate.of(year.getValue(), month, 1);
    LocalDate end = start.plusMonths(1).minusDays(1);
    return expenseRepository.findByDateBetween(DateUtils.asDate(start), DateUtils.asDate(end));
  }

  @Override
  public List<Expense> findExpensesByYearAndMonthAndUsername(Year year, Month month, String username) {
    return findAllExpensesByYearAndMonth(year, month)
        .stream()
        .filter(e -> e.getUser().getUsername().equals(username))
        .sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
        .collect(Collectors.toList());
  }

}
