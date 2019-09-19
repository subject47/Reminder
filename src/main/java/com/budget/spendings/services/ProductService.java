package com.budget.spendings.services;

import com.budget.spendings.domain.Product;
import com.budget.spendings.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements CRUDService<Product> {

  private static final Logger log = Logger.getLogger(ProductService.class.getName());

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
    log.info(() -> "Saving product:" + product.getName());
    return productRepository.save(product);
  }

  @Override
  public void delete(Integer id) {
    productRepository.deleteById(id);
  }

}
