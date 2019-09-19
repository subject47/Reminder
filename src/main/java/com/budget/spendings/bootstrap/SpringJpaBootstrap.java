package com.budget.spendings.bootstrap;

import com.budget.spendings.domain.Category;
import com.budget.spendings.domain.Expense;
import com.budget.spendings.domain.Product;
import com.budget.spendings.domain.Role;
import com.budget.spendings.domain.User;
import com.budget.spendings.services.CategoryService;
import com.budget.spendings.services.ExpenseService;
import com.budget.spendings.services.ProductService;
import com.budget.spendings.services.RoleService;
import com.budget.spendings.services.UserService;
import com.budget.spendings.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired
  private ProductService productService;
  @Autowired
  private UserService userService;
  @Autowired
  private RoleService roleService;
  @Autowired
  private ExpenseService expenseService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private Environment environment;


  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (isDevOrLocalEnv()) {
      loadDummyData();
    } else if (roleService.listAll().isEmpty()) {
      loadRoles();
    }
  }

  private boolean isDevOrLocalEnv() {
    List activeEnvironments = Arrays.asList(environment.getActiveProfiles());
    return activeEnvironments.contains("dev") || activeEnvironments.contains("local");
  }

  private void loadDummyData() {
    loadProducts();
    loadRoles();
    loadUsers();
    loadCategories();
    loadExpenses();
    assignUsersToUserRole();
    assignUsersToAdminRole();
  }

  private void loadProducts() {
    Product shirt = new Product();
    shirt.setDescription("Spring Framework Guru Shirt");
    shirt.setPrice(new BigDecimal("18.95"));
    shirt.setImageUrl(
        "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
    shirt.setName("235268845711068308");
    productService.save(shirt);

    Product mug = new Product();
    mug.setDescription("Spring Framework Guru Mug");
    mug.setImageUrl(
        "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
    mug.setName("168639393495335947");
    mug.setPrice(new BigDecimal("11.95"));
    productService.save(mug);
  }

  private void loadUsers() {
    String u = "user";
    String a = "admin";
    User user1 = new User();
    user1.setUsername(u);
    user1.setPassword(u);
    userService.save(user1);

    User user2 = new User();
    user2.setUsername(a);
    user2.setPassword(a);
    userService.save(user2);

  }

  private void loadRoles() {
    Role role = new Role();
    role.setName(Role.USER);
    roleService.save(role);

    Role adminRole = new Role();
    adminRole.setName(Role.ADMIN);
    roleService.save(adminRole);
  }

  private void loadCategories() {
    Category category = new Category();
    category.setName(Category.FOOD);
    category.setDescription("Food spendings");
    category.setPriority(1);
    categoryService.save(category);

    Category category2 = new Category();
    category2.setName(Category.ELECTRONICS);
    category2.setDescription("Electronics spendings");
    category2.setPriority(4);
    categoryService.save(category2);

    Category category3 = new Category();
    category3.setName(Category.MEDICINE);
    category3.setDescription("Medicine spendings");
    category3.setPriority(2);
    categoryService.save(category3);

    Category category4 = new Category();
    category4.setName(Category.UTILITIES);
    category4.setDescription("Utilities spendings");
    category4.setPriority(3);
    categoryService.save(category4);
  }

  private void loadExpenses() {
    User user = userService.listAll().get(0);
    Category category1 = categoryService.findByName(Category.FOOD);
    Date date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense expense1 = new Expense(user, 2000.0, date, "Milk", category1);
    expenseService.save(expense1);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 12));
    Category category2 = categoryService.findByName(Category.FOOD);
    Expense expense2 = new Expense(user, 6000.0, date, "Meat", category2);
    expenseService.save(expense2);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 20));
    Category category3 = categoryService.findByName(Category.FOOD);
    Expense expense3 = new Expense(user, 3000.0, date, "Bread", category3);
    expenseService.save(expense3);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Category category4 = categoryService.findByName(Category.ELECTRONICS);
    Expense expense4 = new Expense(user, 6000.0, date, "TV", category4);
    expenseService.save(expense4);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Category category5 = categoryService.findByName(Category.MEDICINE);
    Expense expense5 = new Expense(user, 1500.0, date, "Pills", category5);
    expenseService.save(expense5);

    date = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    Category category6 = categoryService.findByName(Category.MEDICINE);
    Expense expense6 = new Expense(user, 500.0, date, "Expense 3 description", category6);
    expenseService.save(expense6);

    date = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 30));
    Category category7 = categoryService.findByName(Category.UTILITIES);
    Expense expense7 = new Expense(user, 7500.0, date, "Expense 4 description", category7);
    expenseService.save(expense7);
  }

  private void assignUsersToUserRole() {
    List<Role> roles = roleService.listAll();
    List<User> users = userService.listAll();

    roles.forEach(role -> {
      if (role.getName().equalsIgnoreCase(Role.USER)) {
        users.forEach(user -> {
          if (user.getUsername().equals("user")) {
            user.addRole(role);
            userService.save(user);
          }
        });
      }
    });
  }

  private void assignUsersToAdminRole() {
    List<Role> roles = roleService.listAll();
    List<User> users = userService.listAll();

    roles.forEach(role -> {
      if (role.getName().equalsIgnoreCase(Role.ADMIN)) {
        users.forEach(user -> {
          if (user.getUsername().equals("admin")) {
            user.addRole(role);
            userService.save(user);
          }
        });
      }
    });
  }
}
