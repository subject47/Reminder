package com.budget.spendings.domain;

import javax.persistence.Entity;
import com.google.common.base.Objects;

@Entity
public class Category extends AbstractDomainClass implements Comparable<Category> {

  public static final String FOOD = "Food";
  public static final String ELECTRONICS = "Electronics";
  public static final String MEDICINE = "Medicine";
  public static final String UTILITIES = "Utilities";

  private String name;
  private String description;
  private Integer priority = 0;

  public Category() {}

  public Category(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Category(int id, String name, String description) {
    this(name, description);
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  @Override
  public String toString() {
    return name + "(" + description + ")";
  }

  @Override
  public int hashCode() {
    return (this.id == null) ? Objects.hashCode(name, description) : this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || o.getClass() != this.getClass()) {
      return false;
    }
    if (this == o) {
      return true;
    }
    Category other = (Category) o;
    return Objects.equal(name, other.getName())
        && Objects.equal(description, other.getDescription());
  }

  @Override
  public int compareTo(Category o) {
    int diff = priority.compareTo(o.getPriority());
    if (diff == 0) {
      return name.compareTo(o.name);
    }
    return diff;
  }
}
