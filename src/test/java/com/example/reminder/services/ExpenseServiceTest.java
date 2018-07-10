package com.example.reminder.services;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
public class ExpenseServiceTest {

  @MockBean
  private ExpenseRepository expenseRepository;

  private ExpenseService expenseService = null;

  private Expense expenseForMay;
  private Expense expenseForApril;
  private Expense expenseForJune;

  @Before
  public void setUp() {
    expenseService = new ExpenseServiceImpl(expenseRepository);

    Date date = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 15));
    expenseForApril = new Expense(2000.0, date, "Milk", new Category("Food", "Food spendings"));

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 30));
    expenseForMay =
        new Expense(1000.0, date, "Condoms", new Category("Medicine", "Medicine spendings"));

    date = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    expenseForJune =
        new Expense(3000.0, date, "TV", new Category("Electronics", "Electronics spendings"));
  }

  @Test
  public void findAllExpensesForYearAndMonth_April2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 30));
    when(expenseRepository.findByDateBetween(firstDay, lastDay))
        .thenReturn(Lists.newArrayList(expenseForApril));
    // when
    List<Expense> expenses =
        expenseService.findAllExpensesByYearAndMonth(Year.of(2018), Month.APRIL);
    // then
    assertThat(expenses).containsExactly(expenseForApril);
  }

  @Test
  public void findAllExpensesForYearAndMonth_May2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    when(expenseRepository.findByDateBetween(firstDay, lastDay))
        .thenReturn(Lists.newArrayList(expenseForMay));
    // when
    List<Expense> expenses =
        expenseService.findAllExpensesByYearAndMonth(Year.of(2018), Month.MAY);
    // then
    assertThat(expenses).containsExactly(expenseForMay);
  }

  @Test
  public void findAllExpensesForYearAndMonth_June2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 30));
    when(expenseRepository.findByDateBetween(firstDay, lastDay))
        .thenReturn(Lists.newArrayList(expenseForJune));
    // when
    List<Expense> expenses =
        expenseService.findAllExpensesByYearAndMonth(Year.of(2018), Month.JUNE);
    // then
    assertThat(expenses).containsExactly(expenseForJune);
  }

}
