package com.budget.spendings.repositories;

import com.budget.spendings.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

  Product findByName(String name);
	
}
