package com.example.reminder.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.User;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
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

    Date date1 = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense e1 = new Expense(2000.0, date1, "Milk", new Category(2, "Food", "Food spendings"));
    e1.setUser(user);
    expenses.add(e1);

    Date date2 = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 15));
    Expense e2 = new Expense(1000.0, date2, "Condoms", new Category(1, "Medicine", "Medicine spendings"));
    e2.setUser(user);
    expenses.add(e2);

    Date date3 = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Expense e3 = new Expense(3000.0, date3, "TV", new Category(3, "Electronics", "Electronics spendings"));
    e3.setUser(user);
    expenses.add(e3);

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
