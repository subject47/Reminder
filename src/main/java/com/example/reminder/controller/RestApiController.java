package com.example.reminder.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;

@RestController
public class RestApiController {

  @Autowired
  private ExpenseService expenseService;

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/allexpenses", method = RequestMethod.GET)
  public List<Expense> listAllExpenses() {
    List<Expense> expenses = (List<Expense>) expenseService.listAll();
    return expenses;
  }

  @RequestMapping(value = "/allcategories", method = RequestMethod.GET)
  public List<Category> listAllCategories() {
    List<Category> categories = categoryService.listAll();
    return categories;
  }

}
