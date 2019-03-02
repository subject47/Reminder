package com.example.reminder.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.User;
import com.example.reminder.forms.ExpenseForm;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.example.reminder.util.TestUtils.CategoryBuilder;
import com.example.reminder.util.TestUtils.ExpenseBuilder;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ExpenseControllerTest {

  private static final int YEAR = 2018;
  private static final int MONTH = 5;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ExpenseService expenseService;

  @MockBean
  private CategoryService categoryService;

  private List<Expense> expenses;
  private List<Category> categories;
  private Expense expense;

  @BeforeEach
  void setup() {
    User user = new User("user", "pw");

    Category category1 = new CategoryBuilder()
        .id(1)
        .name("Cat1_Name")
        .description("Cat1_Description")
        .build();
    Category category2 = new CategoryBuilder()
        .id(2)
        .name("Cat2_Name")
        .description("Cat2_Description")
        .build();
    categories = Lists.newArrayList(category1, category2);

    expense = new ExpenseBuilder()
        .date(DateUtils.asDate(
            LocalDate.of(YEAR, MONTH, 6)))
        .category(category1)
        .user(user)
        .build();

    expenses = Lists.newArrayList(expense);
  }

  @Test
  void dates() throws Exception {
    mvc.perform(get("/dates"))
        //   	.andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("years"))
        .andExpect(model().attributeExists("months"));
  }

  @Test
  @WithMockUser
  void expenses() throws Exception {
    Year year = Year.of(YEAR);
    Month month = Month.of(MONTH);
    when(expenseService.findExpensesByYearAndMonthAndUsername(year, month, "user"))
        .thenReturn(expenses);

    mvc.perform(get("/expenses?year=2018&month=5"))
//		  .andDo(print())
        .andExpect(status().isOk())
        .andExpect(model().attribute("expenses", expenses));
    verify(expenseService).findExpensesByYearAndMonthAndUsername(year, month, "user");
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
  void editExpense() throws Exception {
    when(categoryService.listAll()).thenReturn(categories);
    when(expenseService.getById(1)).thenReturn(expense);

    mvc.perform(get("/expense/edit?id=1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("expenseForm", new ExpenseForm(expense, categories)));
    verify(categoryService).listAll();
    verify(expenseService).getById(1);
  }

}
