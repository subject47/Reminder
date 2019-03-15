package com.example.reminder.repositories;

import com.example.reminder.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {

  Product findByName(String name);
	
}
