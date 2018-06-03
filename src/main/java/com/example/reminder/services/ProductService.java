package com.example.reminder.services;

import com.example.reminder.domain.Product;

public interface ProductService {
	Iterable<Product> listAllProducts();

    Product getProductById(Integer id);

    Product saveProduct(Product product);

    void deleteProduct(Integer id);
}
