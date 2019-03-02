package com.example.reminder.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
        .description("Condoms")
        .category(
            new CategoryBuilder()
                .id(1)
                .name("Medicine")
                .description("Medicine spendings")
                .build())
        .build();

    Expense expense2 = new ExpenseBuilder()
        .user(user)
        .amount(2000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 1)))
        .description("Milk")
        .category(
            new CategoryBuilder()
                .id(2)
                .name("Food")
                .description("Food spendings")
                .build())
        .build();

    Expense expense3 = new ExpenseBuilder()
        .user(user)
        .amount(3000.0)
        .date(
            DateUtils.asDate(
                LocalDate.of(2018, Month.MAY, 31)))
        .description("TV")
        .category(
            new CategoryBuilder()
                .id(3)
                .name("Electronics")
                .description("Electronics spendings")
                .build())
        .build();

    expenses.add(expense1);
    expenses.add(expense2);
    expenses.add(expense3);
  }

  @Test
  void generateChartData() throws Exception {
    when(expenseService.findExpensesByYearAndMonthAndUsername(Year.of(2018), Month.MAY, "user"))
        .thenReturn(expenses);

    mvc.perform(get("/chartdata?date=1525122000000")
        .contentType(MediaType.APPLICATION_JSON))
//        	.andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(
            "{\"cols\":[{\"id\":\"\",\"label\":\"Category\",\"pattern\":\"\",\"type\":\"string\"},{\"id\":\"\",\"label\":\"Amount\",\"pattern\":\"\",\"type\":\"number\"}],\"rows\":[{\"c\":[{\"v\":\"Medicine\",\"f\":null},{\"v\":1000.0,\"f\":null}]},{\"c\":[{\"v\":\"Food\",\"f\":null},{\"v\":2000.0,\"f\":null}]},{\"c\":[{\"v\":\"Electronics\",\"f\":null},{\"v\":3000.0,\"f\":null}]}]}"));

    verify(expenseService).findExpensesByYearAndMonthAndUsername(Year.of(2018), Month.MAY, "user");
  }
}
