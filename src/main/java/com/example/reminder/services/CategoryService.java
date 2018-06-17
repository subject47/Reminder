package com.example.reminder.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reminder.domain.Category;
import com.example.reminder.repositories.CategoryRepository;
import com.google.common.collect.Lists;

@Service
public class CategoryService implements CRUDService<Category> {

  private CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<Category> listAll() {
    List<Category> categories = Lists.newArrayList();
    categoryRepository.findAll().forEach(categories::add);
    return categories;
  }

  @Override
  public Category getById(Integer id) {
    return categoryRepository.findOne(id);
  }

  @Override
  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  @Override
  public void delete(Integer id) {
    categoryRepository.delete(id);
  }

  public Category findByName(String name) {
    return categoryRepository.findByName(name);
  }

}
