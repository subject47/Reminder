package com.example.reminder.bootstrap;

import com.example.reminder.repositories.ProductRepository;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger log = Logger.getLogger(ProductLoader.class.getName());

  private ProductRepository productRepository;

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {

  }

}
