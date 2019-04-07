package com.example.reminder.forms;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.google.common.base.Objects;
import java.util.Date;
import java.util.List;

public class ExpenseForm {

  private Expense expense;
  private List<Category> categories;
  private int categoryId;
  private Double amount;
  private String date;
  private String description;

  public ExpenseForm() {}

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
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (this == o || this.getClass() == o.getClass()) {
      return true;
    }
    ExpenseForm other = (ExpenseForm) o;
    if (categories.size() != other.getCategories().size()) {
      return false;
    }
    for (Category category : categories) {
      if (!other.getCategories().contains(category)) {
        return false;
      }
    }
    return Objects.equal(categories, other.getCategories())
        && Objects.equal(categoryId, other.getCategoryId())
        && Objects.equal(amount, other.getAmount())
        && Objects.equal(date, other.getDate())
        && Objects.equal(description, other.getDescription());
  }

}
