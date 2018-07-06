package com.example.reminder.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.forms.ExpenseForm;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;

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
  
  private int year = 2018;
  private int month = 5;
  private List<Expense> expenses;
  private List<Category> categories;
  private Expense expense;
  
  @Before
  public void setUp() {
	  Category category1 = new Category("Cat1_Name", "Cat1_Description");
	  category1.setId(1);
	  Category category2 = new Category("Cat2_Name", "Cat2_Description");
	  category2.setId(2);
	  categories = Lists.newArrayList(category1, category2);
	  LocalDate date1 = LocalDate.of(year, month, 6);
	  expense = new Expense(2000.0, DateUtils.asDate(date1), "Expense1", category1);
	  expense.setId(1);
	  LocalDate date2 = LocalDate.of(year, month, 8);
	  Expense expense2 = new Expense(4000.0, DateUtils.asDate(date2), "Expense2", category2);
	  expense2.setId(2);
	  expenses = Lists.newArrayList(expense, expense2);
  }
  
  @Test
  public void dates() throws Exception {
    mvc.perform(get("/dates"))
 //   	.andDo(print())
    	.andExpect(status().isOk())
        .andExpect(model().attributeExists("years"))
        .andExpect(model().attributeExists("months"));
  }
  
  @Test
  public void expenses() throws Exception {
	  when(expenseService.findAllExpensesForYearAndMonth(Year.of(year), Month.of(month))).thenReturn(expenses);

	  mvc.perform(get("/expenses?year=2018&month=5"))
//		  .andDo(print())
		  .andExpect(status().isOk())
		  .andExpect(model().attribute("expenses", expenses));
	  verify(expenseService).findAllExpensesForYearAndMonth(Year.of(year), Month.of(month));
  }
  
//  @Test
  // This method fails because thymeleaf framework can't handle null values during unit testing
  public void newExpense() throws Exception {
	  when(categoryService.listAll()).thenReturn(categories);
	  
	  mvc.perform(get("/expense/new"))
	  	.andExpect(status().isOk())
	  	.andExpect(model().attribute("expenseForm", new ExpenseForm(categories)));
	  verify(categoryService).listAll();
  }
  
  @Test
  public void editExpense() throws Exception {
	  when(categoryService.listAll()).thenReturn(categories);
	  when(expenseService.getById(1)).thenReturn(expense);
	  
	  mvc.perform(get("/expense/edit?id=1"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("expenseForm", new ExpenseForm(expense, categories)));
	  verify(categoryService).listAll();
	  verify(expenseService).getById(1);
  }

}
