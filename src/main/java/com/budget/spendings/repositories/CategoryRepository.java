package com.budget.spendings.repositories;

import org.springframework.data.repository.CrudRepository;

import com.budget.spendings.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	Category findByName(String name);
	
}
