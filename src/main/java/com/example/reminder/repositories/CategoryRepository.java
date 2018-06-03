package com.example.reminder.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.reminder.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	Category findByName(String name);
	
}
