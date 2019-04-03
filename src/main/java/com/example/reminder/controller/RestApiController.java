package com.example.reminder.controller;

import static com.google.common.collect.Lists.newArrayList;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.forms.ChartDataForm;
import com.example.reminder.forms.ChartDataForm.Column;
import com.example.reminder.forms.ChartDataForm.Row;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private CategoryService categoryService;


  @GetMapping("/allexpenses")
  public List<Expense> listAllExpenses() {
    return expenseService.listAll().stream()
        .map(e -> {
          e.setUser(null);
          return e;
        })
        .collect(Collectors.toList());
  }


  @GetMapping("/expensesbycategory")
  public List<Expense> listAllExpensesGroupedByCategory() {
    return groupExpensesByCategory(listAllExpenses());
  }

  // This method creates a Google charts API specific data structure to feed the chart with.
  @GetMapping("/chartdata")
  public ChartDataForm generateChartData(@RequestParam("date") String timestamp, Authentication authentication) {
    LocalDate date =
        Instant.ofEpochMilli(Long.parseLong(timestamp))
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    List<Expense> expenses =
        expenseService.findExpensesByYearAndMonthAndUsername(
            Year.of(date.getYear()), date.getMonth(), authentication.getName());

    expenses = groupExpensesByCategory(expenses);

    Collection<Column> columns = newArrayList(new Column("Category", "string"), new Column("Amount", "number"));

    Collection<Row> rows = expenses.stream()
        .map(expense -> {
          Row.Data dataNumber = new Row.Data(expense.getAmount());
          Row.Data dataLabel = new Row.Data(expense.getCategory().getName());
          return new Row(newArrayList(dataLabel, dataNumber));
        }).collect(Collectors.toList());

    return new ChartDataForm(columns, rows);
  }

  @GetMapping("/allcategories")
  public List<Category> listAllCategories() {
    return categoryService.listAll();
  }

  private List<Expense> groupExpensesByCategory(List<Expense> expenses) {
    Map<Integer, List<Expense>> expensesByCategory =
        expenses.stream().collect(Collectors.groupingBy(e -> e.getCategory().getId()));

    List<Expense> groupedExpenses = newArrayList();
    expensesByCategory.entrySet().forEach(entry -> {
      final Expense groupExpense = new Expense();
      groupExpense.setAmount(0.0);
      entry.getValue().forEach(expense -> {
        groupExpense.setCategory(expense.getCategory());
        groupExpense.addAmount(expense.getAmount());
      });
      groupedExpenses.add(groupExpense);
    });
    return groupedExpenses;
  }
}
