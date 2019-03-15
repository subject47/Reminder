package com.example.reminder.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.example.reminder.domain.Product;
import com.example.reminder.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ProductControllerTest {

  @Mock
  private ProductService productService;

  @InjectMocks
  private ProductController productController;

  @Mock
  private View mockView;

  private MockMvc mvc;

  @BeforeEach
  void setup() {
    mvc = MockMvcBuilders.standaloneSetup(productController)
        .setSingleView(mockView)
        .build();
  }

  @Test
  void newProduct() throws Exception {
    mvc.perform(get("/product/new"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("product"))
        .andExpect(view().name("productform"));
  }

  @Test
  void saveProduct() throws Exception {
    // given
    int productId = 1;
    Product product = new Product();
    Mockito.when(productService.save(product)).thenAnswer(i -> {
        Product p = i.getArgument(0);
        p.setId(productId);
        return null;
    });

    // when-then
    mvc.perform(post("/product", product))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("redirect:/product/" + productId));

    Mockito.verify(productService, Mockito.only()).save(product);
  }



}
