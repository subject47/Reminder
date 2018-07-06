package com.example.reminder.domain;

import javax.persistence.Entity;
import com.google.common.base.Objects;

@Entity
public class Category extends AbstractDomainClass {

  private String name;
  private String description;

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
    if (o == null) {
      return false;
    }
    if (!(o instanceof Category)) {
      return false;
    }
    if (this == o) {
      return true;
    }
    Category other = (Category) o;
    if (this.id != null && this.id.equals(other.getId())) {
    	return true;
    }
    return Objects.equal(name, other.getName())
        && Objects.equal(description, other.getDescription());
  }
}
