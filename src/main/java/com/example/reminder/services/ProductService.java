package com.example.reminder.services;

import com.example.reminder.domain.Role;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.reminder.domain.Product;
import com.example.reminder.repositories.ProductRepository;

@Service
public class ProductService extends CRUDService<Product> {

	private ProductRepository productRepository;
	
	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Override
	public List<Product> listAll() {
		List<Product> products = new ArrayList<>();
		productRepository.findAll().forEach(products::add);
		return products;
	}

	@Override
	public Product getById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public void delete(Integer id) {
		productRepository.deleteById(id);
	}

}
