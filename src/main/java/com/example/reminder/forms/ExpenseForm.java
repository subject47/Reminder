package com.example.reminder.forms;

import java.util.Date;
import java.util.List;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;

public class ExpenseForm {

  private int id;
  private int version;
  private Double amount;
  private Date date;
  private String description;
  private List<Category> categories;
  private int categoryId;

  public ExpenseForm() {}

  public ExpenseForm(Expense expense, List<Category> categories) {
    this.id = expense.getId();
    this.version = expense.getVersion();
    this.amount = expense.getAmount();
    this.date = expense.getDate();
    this.categoryId = expense.getCategory().getId();
    this.description = expense.getDescription();
    this.categories = categories;
  }

  public ExpenseForm(List<Category> categories) {
    this.categories = categories;
  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
