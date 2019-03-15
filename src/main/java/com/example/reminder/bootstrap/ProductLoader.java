package com.example.reminder.bootstrap;

import com.example.reminder.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

  private ProductRepository productRepository;

  @Autowired
  public ProductLoader(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    // TODO: Add products
  }

}
