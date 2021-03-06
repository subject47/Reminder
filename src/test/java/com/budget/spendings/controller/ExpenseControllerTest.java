package com.budget.spendings.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.budget.spendings.domain.Category;
import com.budget.spendings.domain.Expense;
import com.budget.spendings.domain.User;
import com.budget.spendings.forms.DataGridForm;
import com.budget.spendings.forms.ExpenseForm;
import com.budget.spendings.services.CategoryService;
import com.budget.spendings.services.ExpenseService;
import com.budget.spendings.services.UserService;
import com.budget.spendings.util.TestUtils.CategoryBuilder;
import com.budget.spendings.util.TestUtils.ExpenseBuilder;
import com.budget.spendings.utils.DateUtils;
import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import org.hamcrest.Matchers;
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

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

  private static final int YEAR = 2018;
  private static final int MONTH = 5;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

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
  void expenses() throws Exception {
    Year year = Year.of(YEAR);
    Month month = Month.of(MONTH);
    int day = 1;
    String category = "category";
    String user = "user";
    when(expenseService.findExpensesByYearMonthDayCategoryAndUsername(year, month, day, category, user))
        .thenReturn(expenses);

    mvc.perform(get("/expenses?year=2018&month=5&day=1&category=category"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("data", expenses))
        .andExpect(model().attributeExists("years"))
        .andExpect(model().attributeExists("months"))
        .andExpect(model().attributeExists("year"))
        .andExpect(model().attributeExists("month"))
        .andExpect(model().attributeExists("endpoint"))
        .andExpect(view().name("expenses"));

    verify(expenseService, times(1)).findExpensesByYearMonthDayCategoryAndUsername(year, month, day, category, user);
  }

  @Test
  public void expense_new() throws Exception {
    when(categoryService.listAll()).thenReturn(categories);
    ExpenseForm form = new ExpenseForm();
    form.setCategories(categories);

    mvc.perform(get("/expense/new"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("expenseForm", Matchers.equalTo(form)));

    verify(categoryService, times(1)).listAll();
    verify(categoryService, never()).findByName(anyString());
  }

  @Test
  public void expense_new_withParameters() throws Exception {
    when(categoryService.listAll()).thenReturn(categories);
    Category category = new Category();
    category.setId(1);
    when(categoryService.findByName("category_name")).thenReturn(category);
    ExpenseForm form = new ExpenseForm();
    form.setCategories(categories);
    form.setDate("2019-05-12");
    form.setCategoryId(1);

    mvc.perform(get("/expense/new?year=2019&month=5&day=12&category=category_name"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("expenseForm", Matchers.equalTo(form)));

    verify(categoryService, times(1)).listAll();
    verify(categoryService, times(1)).findByName("category_name");
  }

  @Test
  void expense_edit() throws Exception {
    when(categoryService.listAll()).thenReturn(categories);
    when(expenseService.getById(1)).thenReturn(expense);

    mvc.perform(get("/expense/edit?id=1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("expenseForm", buildExpenseForm()))
        .andExpect(view().name("expenseForm"));

    verify(categoryService, times(1)).listAll();
    verify(expenseService, times(1)).getById(1);
  }

  @Test
  void expense_saveOrUpdate() throws Exception {
    when(categoryService.getById(1)).thenReturn(new Category());
    when(userService.findByUsername(anyString())).thenReturn(new User());
    mvc.perform(post("/expense")
        .param("categoryId", "1")
        .param("amount", "9000.0")
        .param("date", "2018-04-16")
        .param("description", "expense description").with(csrf()))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/datagrid?year=2018&month=4"));
  }

  @Test
  void datagrid() throws Exception {
    Year year = Year.of(YEAR);
    Month month = Month.of(MONTH);
    DataGridForm form = new DataGridForm();
    when(expenseService.buildDataGrid(year, month, "user")).thenReturn(form);

    mvc.perform(get("/datagrid?year=2018&month=5"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("data", form))
        .andExpect(model().attributeExists("years"))
        .andExpect(model().attributeExists("months"))
        .andExpect(model().attributeExists("year"))
        .andExpect(model().attributeExists("month"))
        .andExpect(view().name("datagrid"));

    verify(expenseService, times(1)).buildDataGrid(year, month, "user");
  }

  private ExpenseForm buildExpenseForm() {
    ExpenseForm form = new ExpenseForm();
    form.setExpense(expense);
    form.setCategories(categories);
    form.setAmount(expense.getAmount());
    form.setDate(DATE_FORMATTER.format(expense.getDate()));
    form.setDescription(expense.getDescription());
    form.setCategoryId(expense.getCategory().getId());
    return form;
  }
}
