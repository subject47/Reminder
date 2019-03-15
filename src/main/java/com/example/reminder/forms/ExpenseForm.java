package com.example.reminder.forms;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.google.common.base.Objects;
import java.util.List;

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

  @Override
  public int hashCode() {
    if (categories == null) {
      return Objects.hashCode(expense, categoryId);
    } else {
      Object[] objects = new Object[categories.size() + 2];
      for (int i = 0; i < categories.size(); i++) {
        objects[i] = categories.get(i);
      }
      objects[categories.size()] = expense;
      objects[categories.size() + 1] = categoryId;
      return Objects.hashCode(objects);
    }
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
    return Objects.equal(expense, other.getExpense())
        && Objects.equal(categoryId, other.getCategoryId());
  }

}
