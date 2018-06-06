package com.example.reminder.services;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private static final Logger logger = Logger.getLogger(ExpenseServiceImpl.class);

  private ExpenseRepository expenseRepository;

  @Autowired
  public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  public List<?> listAll() {
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
    Calendar startDate = GregorianCalendar.getInstance();
    startDate.set(Calendar.YEAR, year.getValue());
    startDate.set(Calendar.MONTH, month.getValue() - 1);
    startDate.set(Calendar.DATE, 1);
    startDate.add(Calendar.DATE, -1);

    Calendar endDate = GregorianCalendar.getInstance();
    endDate.set(Calendar.YEAR, year.getValue());
    endDate.set(Calendar.MONTH, month.getValue() - 1);
    endDate.set(Calendar.DATE, 1);
    endDate.add(Calendar.MONTH, 1);
    endDate.add(Calendar.DATE, -1);
    return expenseRepository.findByDateBetween(startDate.getTime(), endDate.getTime());
  }

}
