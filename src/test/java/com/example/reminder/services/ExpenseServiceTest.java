package com.example.reminder.services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ExpenseServiceTest {

  @MockBean
  private ExpenseRepository expenseRepository;
  @MockBean
  private CategoryService categoryService;

  private ExpenseService sut;

  private Expense expenseForMay = new Expense();
  private Expense expenseForApril = new Expense();
  private Expense expenseForJune = new Expense();

  @BeforeEach
  void setup() {
    sut = new ExpenseService(expenseRepository, categoryService);
  }

  @Test
  void findAllExpensesForYearAndMonth_April2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 30));
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(Lists.newArrayList(expenseForApril));
    // when
    List<Expense> expenses = sut.findAllExpensesByYearAndMonth(Year.of(2018), Month.APRIL);
    // then
    assertThat(expenses).containsExactly(expenseForApril);
  }

  @Test
  void findAllExpensesForYearAndMonth_May2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(Lists.newArrayList(expenseForMay));
    // when
    List<Expense> expenses = sut.findAllExpensesByYearAndMonth(Year.of(2018), Month.MAY);
    // then
    assertThat(expenses).containsExactly(expenseForMay);
  }

  @Test
  void findAllExpensesForYearAndMonth_June2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 30));
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(Lists.newArrayList(expenseForJune));
    // when
    List<Expense> expenses = sut.findAllExpensesByYearAndMonth(Year.of(2018), Month.JUNE);
    // then
    assertThat(expenses).containsExactly(expenseForJune);
  }
}
