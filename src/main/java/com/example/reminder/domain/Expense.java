package com.example.reminder.domain;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.reminder.utils.DateUtils;

@Entity
public class Expense extends AbstractDomainClass implements Comparable<Expense> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @ManyToOne
  @NotNull
  private User user;
  @NotNull
  private Double amount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;
  @OneToOne
  @NotNull
  private Category category;
  private String description;

  public Expense() {}

  public Expense(User user, Double amount, Date date, String description, Category category) {
    this.user = user;
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.category = category;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public void addAmount(Double amount) {
    this.amount += amount;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return String.format("Expense: %,.2f | %s | %s", amount,
        FORMATTER.format(DateUtils.asLocalDate(date)), category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, amount, date, description, category);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    if (this == o) {
      return true;
    }
    Expense other = (Expense) o;
    return Objects.equals(user, other.getUser())
        && Objects.equals(amount, other.getAmount())
        && Objects.equals(date, other.getDate())
        && Objects.equals(description, other.getDescription())
        && Objects.equals(category, other.getCategory());
  }

  @Override
  public int compareTo(Expense o) {
    return date.compareTo(o.getDate());
  }
}
