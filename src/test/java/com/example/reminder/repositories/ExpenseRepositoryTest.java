package com.example.reminder.repositories;

import static com.google.common.truth.Truth.assertThat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExpenseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ExpenseRepository expenseRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  private List<Expense> expensesForMay;
  private List<Expense> expensesForApril;
  private List<Expense> expensesForJune;

  @Before
  public void setUp() {
    loadExpenses();
  }

  @Test
  public void findByDateBetween_May2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    // when
    List<Expense> expenses = expenseRepository.findByDateBetween(firstDay, lastDay);
    // then
    assertThat(expenses).containsAllIn(expensesForMay);
  }

  private void loadExpenses() {
    loadCategories();

    expensesForMay = Lists.newArrayList();
    expensesForApril = Lists.newArrayList();
    expensesForJune = Lists.newArrayList();

    Calendar calendar = GregorianCalendar.getInstance();

    calendar.set(2018, Month.MAY.getValue() - 1, 1);
    Category category1 = categoryRepository.findByName("Food");
    Expense expense1 = new Expense(2000.0, calendar.getTime(), "Milk", category1);
    expensesForMay.add(expense1);

    calendar.set(2018, Month.MAY.getValue() - 1, 15);
    Category category2 = categoryRepository.findByName("Food");
    Expense expense2 = new Expense(6000.0, calendar.getTime(), "Meat", category2);
    expensesForMay.add(expense2);

    calendar.set(2018, Month.MAY.getValue() - 1, 31);
    Category category3 = categoryRepository.findByName("Food");
    Expense expense3 = new Expense(3000.0, calendar.getTime(), "Bread", category3);
    expensesForMay.add(expense3);

    calendar.set(2018, Month.JUNE.getValue() - 1, 1);
    Category category4 = categoryRepository.findByName("Medicine");
    Expense expense4 = new Expense(500.0, calendar.getTime(), "Expense 3 description", category4);
    expensesForJune.add(expense4);

    calendar.set(2018, Month.APRIL.getValue() - 1, 30);
    Category category5 = categoryRepository.findByName("Utilities");
    Expense expense5 = new Expense(7500.0, calendar.getTime(), "Expense 4 description", category5);
    expensesForApril.add(expense5);

    entityManager.persist(expense1);
    entityManager.persist(expense2);
    entityManager.persist(expense3);
    entityManager.persist(expense4);
    entityManager.persist(expense5);
    entityManager.flush();
  }

  private void loadCategories() {
    Category category1 = new Category("Food", "Food spendings");
    Category category2 = new Category("Electronics", "Electronics spendings");
    Category category3 = new Category("Medicine", "Medicine spendings");
    Category category4 = new Category("Utilities", "Utilities spendings");
    entityManager.persist(category1);
    entityManager.persist(category2);
    entityManager.persist(category3);
    entityManager.persist(category4);
    entityManager.flush();
  }

}
