package com.example.reminder.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.reminder.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

	
}
