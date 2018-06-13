package com.example.reminder.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private ExpenseService expenseService;
  @MockBean
  private CategoryService categoryService;

  @Test
  public void dates() throws Exception {
    mvc.perform(get("/dates")).andDo(print()).andExpect(status().isOk())
        .andExpect(model().attributeExists("dateform"));
  }

}
