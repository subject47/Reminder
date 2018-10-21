package com.example.reminder.domain;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.reminder.utils.DateUtils;
import com.google.common.base.Objects;

@Entity
public class Expense extends AbstractDomainClass {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @ManyToOne
  private User user;
  private Double amount;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;
  @OneToOne
  private Category category;
  private String description;

  public Expense() {}

  public Expense(Double amount, Date date, String description, Category category) {
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
    return (this.id == null) ? Objects.hashCode(amount, date, description, category) : this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof Expense)) {
      return false;
    }
    if (this == o) {
      return true;
    }
    Expense other = (Expense) o;
    if (this.id != null && this.id.equals(other.getId())) {
    	return true;
    }
    return Objects.equal(amount, other.getAmount()) && Objects.equal(date, other.getDate())
        && Objects.equal(description, other.getDescription())
        && Objects.equal(category, other.getCategory());
  }

}
