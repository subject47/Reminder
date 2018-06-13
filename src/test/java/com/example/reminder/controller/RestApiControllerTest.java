package com.example.reminder.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestApiControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private ExpenseService expenseService;
  @MockBean
  private CategoryService categoryService;

  private List<Expense> expenses;

  @Before
  public void setUp() {
    expenses = Lists.newArrayList();

    Date date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    expenses.add(new Expense(2000.0, date, "Milk", new Category(2, "Food", "Food spendings")));

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 15));
    expenses.add(
        new Expense(1000.0, date, "Condoms", new Category(1, "Medicine", "Medicine spendings")));

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    expenses.add(
        new Expense(3000.0, date, "TV", new Category(3, "Electronics", "Electronics spendings")));

  }

  @Test
  public void generateChartData() throws Exception {
    when(expenseService.findAllExpensesForYearAndMonth(Year.of(2018), Month.MAY))
        .thenReturn(expenses);

    mvc.perform(post("/chartdata?date=1525122000000").contentType(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk()).andExpect(content().string(
            "{\"cols\":[{\"id\":\"\",\"label\":\"Category\",\"pattern\":\"\",\"type\":\"string\"},{\"id\":\"\",\"label\":\"Amount\",\"pattern\":\"\",\"type\":\"number\"}],\"rows\":[{\"c\":[{\"v\":\"Medicine\",\"f\":null},{\"v\":1000.0,\"f\":null}]},{\"c\":[{\"v\":\"Food\",\"f\":null},{\"v\":2000.0,\"f\":null}]},{\"c\":[{\"v\":\"Electronics\",\"f\":null},{\"v\":3000.0,\"f\":null}]}]}"));
  }
}
