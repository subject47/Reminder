package com.example.reminder.services;

import static com.example.reminder.domain.Product.ELECTRONICS;
import static com.example.reminder.domain.Product.FOOD;
import static com.example.reminder.domain.Product.MEDICINE;
import static com.example.reminder.domain.Product.UTILITIES;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.Product;
import com.example.reminder.domain.User;
import com.example.reminder.forms.DataGridForm;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {

  private static final String USERNAME = "Mickey Mouse";

  @Mock
  private ExpenseRepository expenseRepository;
  @Mock
  private CategoryService categoryService;
  @InjectMocks
  private ExpenseService sut;

  private Expense expenseForMay = new Expense();
  private Expense expenseForApril = new Expense();
  private Expense expenseForJune = new Expense();

  private Map<String, Category> categoryByName = buildCategoryByName();

  private User user = new User(USERNAME, "");

  @Test
  void findAllExpensesForYearAndMonth_April2018() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 30));
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(List.of(expenseForApril));
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
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(List.of(expenseForMay));
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
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(List.of(expenseForJune));
    // when
    List<Expense> expenses = sut.findAllExpensesByYearAndMonth(Year.of(2018), Month.JUNE);
    // then
    assertThat(expenses).containsExactly(expenseForJune);
  }

  @Test
  void buildChartData() {

  }

  @Test
  void buildDataGrid_may() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    when(categoryService.listAll()).thenReturn(buildCategories());
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(expensesForMay());
    // when
    DataGridForm result = sut.buildDataGrid(Year.of(2018), Month.MAY, USERNAME);

    assertThat(result).isEqualTo(expectedDataForMay());
  }

  @Test
  void buildDataGrid_february_leapYear() {
    // given
    Date firstDay = DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 1));
    Date lastDay = DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 29));
    when(categoryService.listAll()).thenReturn(buildCategories());
    when(expenseRepository.findByDateBetween(firstDay, lastDay)).thenReturn(expensesForFebruary_leapYear());
    // when
    DataGridForm result = sut.buildDataGrid(Year.of(2016), Month.FEBRUARY, USERNAME);
    // then
    assertThat(result).isEqualTo(expectedDataForFebruary_leapYear());
  }


  private List<Expense> expensesForMay() {
    Date date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense expense1 = new Expense(user, 2000.0, date, "", categoryByName.get(FOOD));
    expense1.setId(111);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense expense2 = new Expense(user, 6000.0, date, "", categoryByName.get(FOOD));
    expense2.setId(222);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense expense3 = new Expense(user, 3000.0, date, "", categoryByName.get(ELECTRONICS));
    expense3.setId(333);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 20));
    Expense expense4 = new Expense(user, 3000.0, date, "", categoryByName.get(UTILITIES));
    expense4.setId(444);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Expense expense5 = new Expense(user, 6000.0, date, "", categoryByName.get(ELECTRONICS));
    expense5.setId(555);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Expense expense6 = new Expense(user, 1500.0, date, "", categoryByName.get(MEDICINE));
    expense6.setId(666);

    return List.of(
        expense1,
        expense2,
        expense3,
        expense4,
        expense5,
        expense6);
  }

  private List<Expense> expensesForFebruary_leapYear() {
    Date date = DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 1));
    Expense expense1 = new Expense(user, 500.0, date, "", categoryByName.get(MEDICINE));
    expense1.setId(111);

    date = DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 29));
    Expense expense2 = new Expense(user, 7500.0, date, "", categoryByName.get(UTILITIES));
    expense2.setId(222);

    return List.of(
        expense1,
        expense2);
  }

  private List<Category> buildCategories() {
    Category category1 = new Category();
    category1.setName(Product.FOOD);
    category1.setPriority(1);

    Category category2 = new Category();
    category2.setName(Product.ELECTRONICS);
    category2.setPriority(4);

    Category category3 = new Category();
    category3.setName(Product.MEDICINE);
    category3.setPriority(2);

    Category category4 = new Category();
    category4.setName(Product.UTILITIES);
    category4.setPriority(3);

    return List.of(category1, category2, category3, category4);
  }

  private Map<String, Category> buildCategoryByName() {
    Category category1 = new Category();
    category1.setName(Product.FOOD);
    category1.setPriority(1);

    Category category2 = new Category();
    category2.setName(Product.ELECTRONICS);
    category2.setPriority(4);

    Category category3 = new Category();
    category3.setName(Product.MEDICINE);
    category3.setPriority(2);

    Category category4 = new Category();
    category4.setName(Product.UTILITIES);
    category4.setPriority(3);

    return Map.of(Product.FOOD, category1,
        Product.ELECTRONICS, category2,
        Product.MEDICINE, category3,
        Product.UTILITIES, category4);
  }

  private DataGridForm expectedDataForMay() {
    DataGridForm form = new DataGridForm();
    form.setHeader(List.of("Date", "Food", "Medicine", "Utilities", "Electronics", "Total"));
    form.setRows(List.of(
        List.of(1, 8000.0, "", "", 3000.0, 11000.0),
        List.of(2, "", "", "", "", ""),
        List.of(3, "", "", "", "", ""),
        List.of(4, "", "", "", "", ""),
        List.of(5, "", "", "", "", ""),
        List.of(6, "", "", "", "", ""),
        List.of(7, "", "", "", "", ""),
        List.of(8, "", "", "", "", ""),
        List.of(9, "", "", "", "", ""),
        List.of(10, "", "", "", "", ""),
        List.of(11, "", "", "", "", ""),
        List.of(12, "", "", "", "", ""),
        List.of(13, "", "", "", "", ""),
        List.of(14, "", "", "", "", ""),
        List.of(15, "", "", "", "", ""),
        List.of(16, "", "", "", "", ""),
        List.of(17, "", "", "", "", ""),
        List.of(18, "", "", "", "", ""),
        List.of(19, "", "", "", "", ""),
        List.of(20, "", "", 3000.0, "", 3000.0),
        List.of(21, "", "", "", "", ""),
        List.of(22, "", "", "", "", ""),
        List.of(23, "", "", "", "", ""),
        List.of(24, "", "", "", "", ""),
        List.of(25, "", "", "", "", ""),
        List.of(26, "", "", "", "", ""),
        List.of(27, "", "", "", "", ""),
        List.of(28, "", "", "", "", ""),
        List.of(29, "", "", "", "", ""),
        List.of(30, "", "", "", "", ""),
        List.of(31, "", 1500.0, "", 6000.0, 7500.0)
    ));
    form.setFooter(List.of("Total", 8000.0, 1500.0, 3000.0, 9000.0, 21500.0));
    return form;
  }

  private DataGridForm expectedDataForFebruary_leapYear() {
    DataGridForm form = new DataGridForm();
    form.setHeader(List.of("Date", "Food", "Medicine", "Utilities", "Electronics", "Total"));
    form.setRows(List.of(
        List.of(1, "", 500.0, "", "", 500.0),
        List.of(2, "", "", "", "", ""),
        List.of(3, "", "", "", "", ""),
        List.of(4, "", "", "", "", ""),
        List.of(5, "", "", "", "", ""),
        List.of(6, "", "", "", "", ""),
        List.of(7, "", "", "", "", ""),
        List.of(8, "", "", "", "", ""),
        List.of(9, "", "", "", "", ""),
        List.of(10, "", "", "", "", ""),
        List.of(11, "", "", "", "", ""),
        List.of(12, "", "", "", "", ""),
        List.of(13, "", "", "", "", ""),
        List.of(14, "", "", "", "", ""),
        List.of(15, "", "", "", "", ""),
        List.of(16, "", "", "", "", ""),
        List.of(17, "", "", "", "", ""),
        List.of(18, "", "", "", "", ""),
        List.of(19, "", "", "", "", ""),
        List.of(20, "", "", "", "", ""),
        List.of(21, "", "", "", "", ""),
        List.of(22, "", "", "", "", ""),
        List.of(23, "", "", "", "", ""),
        List.of(24, "", "", "", "", ""),
        List.of(25, "", "", "", "", ""),
        List.of(26, "", "", "", "", ""),
        List.of(27, "", "", "", "", ""),
        List.of(28, "", "", "", "", ""),
        List.of(29, "", "", 7500.0, "", 7500.0)
    ));
    form.setFooter(List.of("Total", 0.0, 500.0, 7500.0, 0.0, 8000.0));
    return form;
  }
}
