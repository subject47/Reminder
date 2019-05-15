package com.example.reminder.util;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.User;
import java.util.Date;

public class TestUtils {

  public static class ExpenseBuilder {
    private Integer id;
    private User user;
    private Double amount;
    private Date date;
    private Category category;
    private String description;

    public static ExpenseBuilder builder() {
      return new ExpenseBuilder();
    }

    public ExpenseBuilder id(Integer id) {
      this.id = id;
      return this;
    }

    public ExpenseBuilder user(User user) {
      this.user = user;
      return this;
    }

    public ExpenseBuilder amount(Double amount) {
      this.amount = amount;
      return this;
    }

    public ExpenseBuilder date(Date date) {
      this.date = date;
      return this;
    }

    public ExpenseBuilder category(Category category) {
      this.category = category;
      return this;
    }

    public ExpenseBuilder description(String description) {
      this.description = description;
      return this;
    }

    public Expense build() {
      Expense expense = new Expense();
      expense.setId(id);
      expense.setAmount(amount);
      expense.setUser(user);
      expense.setDate(date);
      expense.setCategory(category);
      expense.setDescription(description);
      return expense;
    }
  }


  public static class CategoryBuilder {
    private Integer id;
    private String name;
    private String description;
    private int priority;

    public static CategoryBuilder builder() {
      return new CategoryBuilder();
    }

    public CategoryBuilder id(Integer id) {
      this.id = id;
      return this;
    }

    public CategoryBuilder name(String name) {
      this.name = name;
      return this;
    }

    public CategoryBuilder description(String description) {
      this.description = description;
      return this;
    }

    public CategoryBuilder priority(int priority) {
      this.priority = priority;
      return this;
    }

    public Category build() {
      Category category = new Category();
      category.setId(id);
      category.setName(name);
      category.setDescription(description);
      category.setPriority(priority);
      return category;
    }
  }

}
