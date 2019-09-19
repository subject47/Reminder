package com.budget.spendings.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.budget.spendings.forms.ChartDataForm;
import com.budget.spendings.services.CategoryService;
import com.budget.spendings.services.ExpenseService;
import java.time.Month;
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

  private ChartDataForm chartData;

  @BeforeEach
  void setUp() {
    List<ChartDataForm.Column> columns = List.of(
        new ChartDataForm.Column("Category", "string"),
        new ChartDataForm.Column("Amount", "number"));
    List<ChartDataForm.Row> rows = List.of(
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Medicine"),
                new ChartDataForm.Row.Data(1300.0)
            )),
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Food"),
                new ChartDataForm.Row.Data(3500.0)
            )
        ),
        new ChartDataForm.Row(
            List.of(
                new ChartDataForm.Row.Data("Electronics"),
                new ChartDataForm.Row.Data(3000.0)
            )
        )

    );
    chartData = new ChartDataForm(columns, rows);
  }

  @Test
  void generateChartData() throws Exception {
    when(expenseService.buildChartData(2018, Month.MAY.getValue(), "user"))
        .thenReturn(chartData);

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
    verify(expenseService).buildChartData(2018, Month.MAY.getValue(), "user");
  }
}
