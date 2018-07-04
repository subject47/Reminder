package com.example.reminder.services;

import com.example.reminder.domain.Category;

public interface CategoryService extends CRUDService<Category> {

  public Category findByName(String name);

}
