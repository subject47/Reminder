package com.example.reminder.controller;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.forms.ExpenseForm;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExpenseController {

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private CategoryService categoryService;
  
  private static final List<String> YEARS =
		  Lists.newArrayList("2018", "2019", "2020", "2021", "2022");
  private static final ImmutableMap<Integer, String> MONTHS = 
      ImmutableMap.<Integer, String>builder()
		.put(Month.JANUARY.getValue(), "January")
		.put(Month.FEBRUARY.getValue(), "February")
		.put(Month.MARCH.getValue(), "March")
		.put(Month.APRIL.getValue(), "April")
		.put(Month.MAY.getValue(), "May")
		.put(Month.JUNE.getValue(), "June")
		.put(Month.JULY.getValue(), "July")
		.put(Month.AUGUST.getValue(), "August")
		.put(Month.SEPTEMBER.getValue(), "September")
		.put(Month.OCTOBER.getValue(), "October")
		.put(Month.NOVEMBER.getValue(), "November")
		.put(Month.DECEMBER.getValue(), "December")
		.build();


  @RequestMapping("/dates")
  public String dates(Model model) {	  
    model.addAttribute("years", YEARS);
    model.addAttribute("months", MONTHS);
    model.addAttribute("year", 1);
    model.addAttribute("month", 1);
    return "dates";
  }
  
  @RequestMapping(value = "/expenses", method = RequestMethod.GET)
  public String expenses(@RequestParam Integer year, @RequestParam Integer month, Model model) {
    List<Expense> expenses = expenseService
        .findAllExpensesForYearAndMonth(Year.of(year), Month.of(month))
        .stream().sorted((e1, e2) -> e1.getDate().compareTo(e2.getDate()))
        .collect(Collectors.toList());
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
  
  @RequestMapping(value = "/expense/edit")
  public String editExpense(@RequestParam Integer id, Model model) {
    List<Category> categories = categoryService.listAll();
    Expense expense = expenseService.getById(id);
    ExpenseForm form = new ExpenseForm(expense, categories);
    model.addAttribute("expenseForm", form);
    return "expenseForm";
  }

  @RequestMapping(value = "/expense")
  public String expense(ExpenseForm expenseForm) {
    int categoryId = expenseForm.getCategoryId();
    Category category = categoryService.getById(categoryId);
    Expense expense = expenseForm.getExpense();
    expense.setCategory(category);
    expenseService.save(expense);
    return "index";
  }

}
