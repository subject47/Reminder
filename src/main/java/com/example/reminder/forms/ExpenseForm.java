package com.example.reminder.forms;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import java.util.List;
import java.util.Objects;

public class ExpenseForm {

  private Expense expense = new Expense();
  private List<Category> categories;
  private int categoryId;
  private Double amount;
  private String date;
  private String description;

  public ExpenseForm() {
  }

  public ExpenseForm(List<Category> categories) {
    this.categories = categories;
    this.expense = new Expense();
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

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Double getAmount() {
    return amount;
  }

  public String getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public int hashCode() {
    return Objects.hash(expense, categories, expense, categoryId, amount, date, description);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    if (this == o) {
      return true;
    }
    ExpenseForm other = (ExpenseForm) o;
    return Objects.equals(expense, other.getExpense())
        && Objects.equals(categories, other.getCategories())
        && Objects.equals(categoryId, other.getCategoryId())
        && Objects.equals(amount, other.getAmount())
        && Objects.equals(date, other.getDate())
        && Objects.equals(description, other.getDescription());
  }
}
