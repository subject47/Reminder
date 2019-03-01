package com.example.reminder.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.forms.ChartDataForm;
import com.google.common.collect.Lists;

@RestController
public class RestApiController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value = "/allexpenses", method = RequestMethod.GET)
    public List<Expense> listAllExpenses() {
        List<Expense> expenses = expenseService.listAll();
        expenses = expenses.stream().map(e -> {
            e.setUser(null);
            return e;
        }).collect(Collectors.toList());
        return expenses;
    }


    @RequestMapping(value = "/expensesbycategory", method = RequestMethod.GET)
    public List<Expense> listAllExpensesGroupedByCategory() {
        return groupExpensesByCategory(listAllExpenses());
    }

    // This method creates a Google charts API specific data structure to feed the chart with.
    @RequestMapping(value = "/chartdata", method = RequestMethod.GET)
    public ChartDataForm generateChartData(@RequestParam("date") String timestamp, Authentication authentication) {
        LocalDate date = Instant.ofEpochMilli(Long.parseLong(timestamp)).atZone(ZoneId.systemDefault())
                .toLocalDate();
        List<Expense> expenses =
                expenseService.findExpensesByYearAndMonthAndUsername(
                        Year.of(date.getYear()), date.getMonth(), authentication.getName());

        expenses = groupExpensesByCategory(expenses);

        Collection<ChartDataForm.Column> columns =
                Lists.newArrayList(new ChartDataForm.Column("Category", "string"),
                        new ChartDataForm.Column("Amount", "number"));
        Collection<ChartDataForm.Row> rows = Lists.newArrayList();

        expenses.stream().forEach(expense -> {
            Category category = expense.getCategory();
            ChartDataForm.Row.Data dataNumber = new ChartDataForm.Row.Data(expense.getAmount());
            ChartDataForm.Row.Data dataLabel = new ChartDataForm.Row.Data(category.getName());
            ChartDataForm.Row row = new ChartDataForm.Row(Lists.newArrayList(dataLabel, dataNumber));
            rows.add(row);
        });
        return new ChartDataForm(columns, rows);
    }


    @RequestMapping(value = "/allcategories", method = RequestMethod.GET)
    public List<Category> listAllCategories() {
        List<Category> categories = categoryService.listAll();
        return categories;
    }


    private List<Expense> groupExpensesByCategory(List<Expense> expenses) {
        Map<Integer, List<Expense>> expensesByCategory =
                expenses.stream().collect(Collectors.groupingBy(e -> e.getCategory().getId()));

        List<Expense> groupedExpenses = Lists.newArrayList();
        for (Map.Entry<Integer, List<Expense>> entry : expensesByCategory.entrySet()) {
            Expense groupExpense = null;
            for (Expense expense : entry.getValue()) {
                if (groupExpense == null) {
                    groupExpense = new Expense();
                    groupExpense.setCategory(expense.getCategory());
                    groupExpense.setAmount(0.0);
                }
                groupExpense.addAmount(expense.getAmount());
            }
            groupedExpenses.add(groupExpense);
        }
        return groupedExpenses;
    }

}
