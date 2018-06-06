package com.example.reminder.forms;

import java.util.List;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;

public class ExpenseForm {

  private Expense expense;

  private List<Category> categories;
  private int categoryId;

  public ExpenseForm() {}

  public ExpenseForm(Expense expense, List<Category> categories) {
    this.expense = expense;
    this.categoryId = expense.getCategory().getId();
    this.categories = categories;
  }

  public ExpenseForm(List<Category> categories) {
    this.categories = categories;
  }

  public Expense getExpense() {
    return expense;
  }

  public void setExpense(Expense expense) {
    this.expense = expense;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

}
