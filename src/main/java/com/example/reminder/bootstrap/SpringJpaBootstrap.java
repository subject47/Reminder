package com.example.reminder.bootstrap;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.domain.Product;
import com.example.reminder.domain.Role;
import com.example.reminder.domain.User;
import com.example.reminder.repositories.ProductRepository;
import com.example.reminder.services.CategoryService;
import com.example.reminder.services.ExpenseService;
import com.example.reminder.services.RoleService;
import com.example.reminder.services.UserService;
import com.example.reminder.utils.DateUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private static final Logger log = Logger.getLogger(SpringJpaBootstrap.class.getName());

  @Autowired
  private ProductRepository productRepository;
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
    shirt.setProductId("235268845711068308");
    productRepository.save(shirt);

    log.info("Saved Shirt - id: " + shirt.getId());

    Product mug = new Product();
    mug.setDescription("Spring Framework Guru Mug");
    mug.setImageUrl(
        "https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
    mug.setProductId("168639393495335947");
    mug.setPrice(new BigDecimal("11.95"));
    productRepository.save(mug);

    log.info("Saved Mug - id:" + mug.getId());
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
    role.setRole(Role.USER);
    roleService.save(role);
    log.info("Saved role" + role.getRole());
    Role adminRole = new Role();
    adminRole.setRole(Role.ADMIN);
    roleService.save(adminRole);
    log.info("Saved role" + adminRole.getRole());
  }

  private void loadCategories() {
    Category category = new Category();
    category.setName("Food");
    category.setDescription("Food spendings");
    categoryService.save(category);

    Category category2 = new Category();
    category2.setName("Electronics");
    category2.setDescription("Electronics spendings");
    categoryService.save(category2);

    Category category3 = new Category();
    category3.setName("Medicine");
    category3.setDescription("Medicine spendings");
    categoryService.save(category3);

    Category category4 = new Category();
    category4.setName("Utilities");
    category4.setDescription("Utilities spendings");
    categoryService.save(category4);
  }

  private void loadExpenses() {
    User user = userService.listAll().get(0);
    Category category1 = categoryService.findByName("Food");
    Date date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 1));
    Expense expense1 = new Expense(user, 2000.0, date, "Milk", category1);
    expenseService.save(expense1);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 12));
    Category category2 = categoryService.findByName("Food");
    Expense expense2 = new Expense(user, 6000.0, date, "Meat", category2);
    expenseService.save(expense2);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 20));
    Category category3 = categoryService.findByName("Food");
    Expense expense3 = new Expense(user, 3000.0, date, "Bread", category3);
    expenseService.save(expense3);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Category category4 = categoryService.findByName("Electronics");
    Expense expense4 = new Expense(user, 6000.0, date, "TV", category4);
    expenseService.save(expense4);

    date = DateUtils.asDate(LocalDate.of(2018, Month.MAY, 31));
    Category category5 = categoryService.findByName("Medicine");
    Expense expense5 = new Expense(user, 1500.0, date, "Pills", category5);
    expenseService.save(expense5);

    date = DateUtils.asDate(LocalDate.of(2018, Month.JUNE, 1));
    Category category6 = categoryService.findByName("Medicine");
    Expense expense6 = new Expense(user, 500.0, date, "Expense 3 description", category6);
    expenseService.save(expense6);

    date = DateUtils.asDate(LocalDate.of(2018, Month.APRIL, 30));
    Category category7 = categoryService.findByName("Utilities");
    Expense expense7 = new Expense(user, 7500.0, date, "Expense 4 description", category7);
    expenseService.save(expense7);
  }

  private void assignUsersToUserRole() {
    List<Role> roles = roleService.listAll();
    List<User> users = userService.listAll();

    roles.forEach(role -> {
      if (role.getRole().equalsIgnoreCase(Role.USER)) {
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
      if (role.getRole().equalsIgnoreCase(Role.ADMIN)) {
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
