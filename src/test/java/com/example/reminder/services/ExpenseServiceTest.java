package com.example.reminder.services;

import static com.example.reminder.domain.Category.ELECTRONICS;
import static com.example.reminder.domain.Category.FOOD;
import static com.example.reminder.domain.Category.MEDICINE;
import static com.example.reminder.domain.Category.UTILITIES;
import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.User;
import com.example.reminder.forms.ChartDataForm;
import com.example.reminder.forms.DataGridForm;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.util.TestUtils.CategoryBuilder;
import com.example.reminder.util.TestUtils.ExpenseBuilder;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;
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
    LocalDate start = LocalDate.of(2018, Month.MAY, 1);
    LocalDate end = start.plusMonths(1).minusDays(1);
    when(expenseRepository.findByDateBetween(DateUtils.asDate(start), DateUtils.asDate(end)))
        .thenReturn(buildExpensesForChart());

    ChartDataForm res = sut.buildChartData(2018, Month.MAY.getValue(), USERNAME);
    System.out.println(res);
    assertThat(res).isEqualTo(buildExpectedChartData());
  }

  @Test
  void groupExpensesByCategory() {
    List<Expense> result = sut.groupExpensesByCategory(buildExpensesForChart());
    assertThat(result).isEqualTo(buildExpensesGroupedByCategories());
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
    Expense expense1 = ExpenseBuilder.builder()
        .id(111)
        .user(user)
        .amount(2000.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1)))
        .category(categoryByName.get(FOOD))
        .build();

    Expense expense2 = ExpenseBuilder.builder()
        .id(222)
        .user(user)
        .amount(6000.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1)))
        .category(categoryByName.get(FOOD))
        .build();

    Expense expense3 = ExpenseBuilder.builder()
        .id(333)
        .user(user)
        .amount(3000.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1)))
        .category(categoryByName.get(ELECTRONICS))
        .build();

    Expense expense4 = ExpenseBuilder.builder()
        .id(444)
        .user(user)
        .amount(3000.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 20)))
        .category(categoryByName.get(UTILITIES)
        ).build();

    Expense expense5 = ExpenseBuilder.builder()
        .id(555)
        .user(user)
        .amount(6000.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31)))
        .category(categoryByName.get(ELECTRONICS))
        .build();

    Expense expense6 = ExpenseBuilder.builder()
        .id(666)
        .user(user)
        .amount(1500.0)
        .date(DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31)))
        .category(categoryByName.get(MEDICINE))
        .build();

    return List.of(
        expense1,
        expense2,
        expense3,
        expense4,
        expense5,
        expense6);
  }

  private List<Expense> expensesForFebruary_leapYear() {
    Expense expense1 = ExpenseBuilder.builder()
        .id(111)
        .user(user)
        .amount(500.0)
        .date(DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 1)))
        .category(categoryByName.get(MEDICINE))
        .build();

    Expense expense2 = ExpenseBuilder.builder()
        .id(222)
        .user(user)
        .amount(7500.0)
        .date(DateUtils.asDate(LocalDate.of(2016, Month.FEBRUARY, 29)))
        .category(categoryByName.get(UTILITIES))
        .build();

    return List.of(
        expense1,
        expense2);
  }

  private List<Category> buildCategories() {
    return List.of(
        CategoryBuilder.builder().name(FOOD).priority(1).build(),
        CategoryBuilder.builder().name(ELECTRONICS).priority(4).build(),
        CategoryBuilder.builder().name(MEDICINE).priority(2).build(),
        CategoryBuilder.builder().name(UTILITIES).priority(3).build());
  }

  private Map<String, Category> buildCategoryByName() {
    return Map.of(
        FOOD, CategoryBuilder.builder().name(FOOD).priority(1).build(),
        ELECTRONICS, CategoryBuilder.builder().name(ELECTRONICS).priority(4).build(),
        MEDICINE, CategoryBuilder.builder().name(MEDICINE).priority(2).build(),
        UTILITIES, CategoryBuilder.builder().name(UTILITIES).priority(3).build());
  }

  private DataGridForm expectedDataForMay() {
    DataGridForm form = new DataGridForm();
    form.setHeader(List.of("Date", "Food", "Medicine", "Utilities", "Electronics", "Total"));

    List<List<Object>> rows = Lists.newArrayListWithCapacity(31);
    for (int i = 1; i <= Month.MAY.length(false); i++) {
      rows.add(Lists.newArrayList(i, "", "", "", "", ""));
    }
    rows.set(0, List.of(1, 8000.0, "", "", 3000.0, 11000.0));
    rows.set(19, List.of(20, "", "", 3000.0, "", 3000.0));
    rows.set(30, List.of(31, "", 1500.0, "", 6000.0, 7500.0));
    form.setRows(rows);
    form.setFooter(List.of("Total", 8000.0, 1500.0, 3000.0, 9000.0, 21500.0));
    return form;
  }

  private DataGridForm expectedDataForFebruary_leapYear() {
    DataGridForm form = new DataGridForm();
    form.setHeader(List.of("Date", "Food", "Medicine", "Utilities", "Electronics", "Total"));
    List<List<Object>> rows = Lists.newArrayListWithCapacity(29);
    for (int i = 1; i <= Month.FEBRUARY.length(true); i++) {
      rows.add(Lists.newArrayList(i, "", "", "", "", ""));
    }
    rows.set(0, List.of(1, "", 500.0, "", "", 500.0));
    rows.set(28, List.of(29, "", "", 7500.0, "", 7500.0));
    form.setRows(rows);
    form.setFooter(List.of("Total", 0.0, 500.0, 7500.0, 0.0, 8000.0));
    return form;
  }

  private List<Expense> buildExpensesForChart() {
    Expense expense1 = new ExpenseBuilder()
        .user(user)
        .amount(1000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 15)))
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .build())
        .build();

    Expense expense2 = new ExpenseBuilder()
        .user(user)
        .amount(300.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 15)))
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .build())
        .build();

    Expense expense3 = new ExpenseBuilder()
        .user(user)
        .amount(2000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 1)))
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .build())
        .build();

    Expense expense4 = new ExpenseBuilder()
        .user(user)
        .amount(1500.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 1)))
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .build())
        .build();

    Expense expense5 = new ExpenseBuilder()
        .user(user)
        .amount(3000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 31)))
        .category(
            new CategoryBuilder()
                .id(3)
                .name("Electronics")
                .build())
        .build();

    return List.of(
        expense1,
        expense2,
        expense3,
        expense4,
        expense5
    );
  }

  private List<Expense> buildExpensesGroupedByCategories() {
    Expense expense1 = new ExpenseBuilder()
        .user(user)
        .amount(1300.0)
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .build())
        .build();

    Expense expense2 = new ExpenseBuilder()
        .user(user)
        .amount(3500.0)
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .build())
        .build();

    Expense expense3 = new ExpenseBuilder()
        .user(user)
        .amount(3000.0)
        .category(
            new CategoryBuilder()
                .id(3)
                .name("Electronics")
                .build())
        .build();

    return List.of(
        expense1,
        expense2,
        expense3
    );
  }

  private ChartDataForm buildExpectedChartData() {
    List<ChartDataForm.Column> columns = List.of(
        new ChartDataForm.Column("Category", "string"),
        new ChartDataForm.Column("Amount", "number"));
    List<ChartDataForm.Row> rows = List.of(
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Medicine"),
                new ChartDataForm.Row.Data(1300.0)
            )),
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Food"),
                new ChartDataForm.Row.Data(3500.0)
            )
        ),
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Electronics"),
                new ChartDataForm.Row.Data(3000.0)
            )
        )

    );
    return new ChartDataForm(columns, rows);
  }
}
