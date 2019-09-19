package com.budget.spendings.controller;

import com.budget.spendings.domain.Category;
import com.budget.spendings.domain.Expense;
import com.budget.spendings.forms.ChartDataForm;
import com.budget.spendings.services.CategoryService;
import com.budget.spendings.services.ExpenseService;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
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
    return expenseService.groupExpensesByCategory(listAllExpenses());
  }

  // This method creates a Google charts API specific data structure to feed the chart with.
  @GetMapping("/chartdata")
  public ChartDataForm generateChartData(@RequestParam("date") String timestamp, Authentication authentication) {
    ZonedDateTime date = Instant.ofEpochMilli(Long.parseLong(timestamp))
        .atZone(ZoneId.systemDefault());
    return expenseService.buildChartData(date.getYear(), date.getMonthValue(), authentication.getName());
  }

  @GetMapping("/allcategories")
  public List<Category> listAllCategories() {
    return categoryService.listAll();
  }
}
