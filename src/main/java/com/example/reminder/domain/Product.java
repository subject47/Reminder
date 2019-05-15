package com.example.reminder.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;

@Entity
public class Product extends AbstractDomainClass {

  private String name;
  private String description;
  private String imageUrl;
  private BigDecimal price;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, imageUrl, price);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (this == o || this.getClass() == o.getClass()) {
      return true;
    }
    Product other = (Product) o;
    return Objects.equals(id, other.getId())
        && Objects.equals(name, other.getName())
        && Objects.equals(description, other.getDescription())
        && Objects.equals(imageUrl, other.getImageUrl())
        && Objects.equals(price, other.getPrice());
  }
}
