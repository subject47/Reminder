package com.budget.spendings.repositories;

import static com.google.common.truth.Truth.assertThat;

import com.budget.spendings.domain.Category;
import com.budget.spendings.domain.Expense;
import com.budget.spendings.domain.User;
import com.budget.spendings.util.TestUtils.ExpenseBuilder;
import com.budget.spendings.utils.DateUtils;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ExpenseRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private ExpenseRepository expenseRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  private List<Expense> expensesForMay;
  private List<Expense> expensesForApril;
  private List<Expense> expensesForJune;

  @BeforeEach
  void setup() {
    loadData();
  }

  @Test
  void findByDateBetween_May2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    // when
    List<Expense> expenses = expenseRepository.findByDateBetween(firstDay, lastDay);
    // then
    assertThat(expenses).containsAllIn(expensesForMay);
  }

  private void loadData() {
    loadCategories();
    entityManager.persist(new User("user", "password"));

    expensesForMay = Lists.newArrayList();
    expensesForApril = Lists.newArrayList();
    expensesForJune = Lists.newArrayList();

    Expense expense1 = new ExpenseBuilder()
        .user(userRepository.findByUsername("user"))
        .amount(2000.0)
        .date(DateUtils.asDate(
            LocalDate.of(2018, Month.MAY, 1)))
        .description("Milk")
        .category(categoryRepository.findByName("Food"))
        .build();

    Expense expense2 = new ExpenseBuilder()
        .user(userRepository.findByUsername("user"))
        .amount(6000.0)
        .date(DateUtils.asDate(
            LocalDate.of(2018, Month.MAY, 15)))
        .description("Meat")
        .category(categoryRepository.findByName("Food"))
        .build();

    Expense expense3 = new ExpenseBuilder()
        .user(userRepository.findByUsername("user"))
        .amount(3000.0)
        .date(DateUtils.asDate(
            LocalDate.of(2018, Month.MAY, 31)))
        .description("Bread")
        .category(categoryRepository.findByName("Food"))
        .build();

    Expense expense4 = new ExpenseBuilder()
        .user(userRepository.findByUsername("user"))
        .amount(500.0)
        .date(DateUtils.asDate(
            LocalDate.of(2018, Month.JUNE, 1)))
        .description("Pills")
        .category(categoryRepository.findByName("Medicine"))
        .build();

    Expense expense5 = new ExpenseBuilder()
        .user(userRepository.findByUsername("user"))
        .amount(7500.0)
        .date(DateUtils.asDate(
            LocalDate.of(2018, Month.APRIL, 30)))
        .description("TV")
        .category(categoryRepository.findByName("Utilities"))
        .build();

    expensesForMay.add(expense1);
    expensesForMay.add(expense2);
    expensesForMay.add(expense3);
    expensesForJune.add(expense4);
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
  }

}
