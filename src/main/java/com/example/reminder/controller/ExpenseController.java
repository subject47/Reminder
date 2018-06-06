package com.example.reminder.controller;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.forms.DateForm;
import com.example.reminder.forms.ExpenseForm;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;

@Controller
public class ExpenseController {

  private static final Logger logger = Logger.getLogger(ExpenseController.class);

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private CategoryService categoryService;

  @RequestMapping("/dates")
  public String dates(Model model) {
    model.addAttribute("dateform", new DateForm());
    return "dates";
  }

  @RequestMapping(value = "/dateSelected", method = RequestMethod.POST)
  public String dateSelected(DateForm dateform, Model model) {
    logger.info("Selected year: " + dateform.getYear() + "; Selected month: "
        + Month.of(dateform.getMonth()));

    List<Expense> expenses = expenseService
        .findAllExpensesForYearAndMonth(Year.of(dateform.getYear()), Month.of(dateform.getMonth()))
        .stream().sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
        .collect(Collectors.toList());

    expenses.stream().forEach(System.out::println);

    model.addAttribute("expenses", expenses);
    return "expenseList";
  }

  @RequestMapping(value = "/expense/new")
  public String newExpense(Model model) {
    List<Category> categories = categoryService.listAll();
    ExpenseForm form = new ExpenseForm(categories);
    model.addAttribute("expenseForm", form);
    return "expenseForm";
  }

  @RequestMapping(value = "/expense")
  public String expense(ExpenseForm expenseForm) {
    int categoryId = expenseForm.getCategoryId();
    Category category = categoryService.getById(categoryId);
    Expense expense = new Expense();
    expense.setCategory(category);
    expense.setAmount(expenseForm.getAmount());
    expense.setDate(expenseForm.getDate());
    expense.setDescription(expenseForm.getDescription());
    expenseService.save(expense);
    return "index";
  }

}
