package com.example.reminder.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reminder.domain.Expense;
import com.example.reminder.domain.User;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class RestApiControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private ExpenseService expenseService;
  @MockBean
  private CategoryService categoryService;

  private List<Expense> expenses;

  @BeforeEach
  void setUp() {
    expenses = Lists.newArrayList();
    User user = new User("user", "pw");

    Expense expense1 = new ExpenseBuilder()
        .user(user)
        .amount(1000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 15)))
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .build())
        .build();

    Expense expense2 = new ExpenseBuilder()
        .user(user)
        .amount(300.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 15)))
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .build())
        .build();

    Expense expense3 = new ExpenseBuilder()
        .user(user)
        .amount(2000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 1)))
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .build())
        .build();

    Expense expense4 = new ExpenseBuilder()
        .user(user)
        .amount(1500.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 1)))
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .build())
        .build();

    Expense expense5 = new ExpenseBuilder()
        .user(user)
        .amount(3000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 31)))
        .category(
            new CategoryBuilder()
                .id(3)
                .name("Electronics")
                .build())
        .build();

    expenses.add(expense1);
    expenses.add(expense2);
    expenses.add(expense3);
    expenses.add(expense4);
    expenses.add(expense5);
  }

  @Test
  void generateChartData() throws Exception {
    when(expenseService.findExpensesByYearAndMonthAndUsername(Year.of(2018), Month.MAY, "user"))
        .thenReturn(expenses);

    mvc.perform(get("/chartdata?date=1525122000000")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.cols", hasSize(2)))
        .andExpect(jsonPath("$.cols[0].id", is("")))
        .andExpect(jsonPath("$.cols[0].label", is("Category")))
        .andExpect(jsonPath("$.cols[0].pattern", is("")))
        .andExpect(jsonPath("$.cols[0].type", is("string")))
        .andExpect(jsonPath("$.cols[1].id", is("")))
        .andExpect(jsonPath("$.cols[1].label", is("Amount")))
        .andExpect(jsonPath("$.cols[1].pattern", is("")))
        .andExpect(jsonPath("$.cols[1].type", is("number")))
        .andExpect(jsonPath("$.rows", hasSize(3)))
        .andExpect(jsonPath("$.rows[0].c", hasSize(2)))
        .andExpect(jsonPath("$.rows[0].c[0].v", is("Medicine")))
        .andExpect(jsonPath("$.rows[0].c[0].f", equalTo(null)))
        .andExpect(jsonPath("$.rows[0].c[1].v", is(1300.0)))
        .andExpect(jsonPath("$.rows[0].c[1].f", equalTo(null)))
        .andExpect(jsonPath("$.rows[1].c", hasSize(2)))
        .andExpect(jsonPath("$.rows[1].c[0].v", is("Food")))
        .andExpect(jsonPath("$.rows[1].c[0].f", equalTo(null)))
        .andExpect(jsonPath("$.rows[1].c[1].v", is(3500.0)))
        .andExpect(jsonPath("$.rows[1].c[1].f", equalTo(null)))
        .andExpect(jsonPath("$.rows[2].c", hasSize(2)))
        .andExpect(jsonPath("$.rows[2].c[0].v", is("Electronics")))
        .andExpect(jsonPath("$.rows[2].c[0].f", equalTo(null)))
        .andExpect(jsonPath("$.rows[2].c[1].v", is(3000.0)))
        .andExpect(jsonPath("$.rows[2].c[1].f", equalTo(null)));
    verify(expenseService).findExpensesByYearAndMonthAndUsername(Year.of(2018), Month.MAY, "user");
  }
}
