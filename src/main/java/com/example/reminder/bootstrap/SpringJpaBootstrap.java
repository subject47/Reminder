package com.example.reminder.bootstrap;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
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

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private ProductRepository productRepository;
  private UserService userService;
  private RoleService roleService;
  private ExpenseService expenseService;
  private CategoryService categoryService;

  private Logger log = Logger.getLogger(SpringJpaBootstrap.class);

  @Autowired
  public void setProductRepository(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setRoleService(RoleService roleService) {
    this.roleService = roleService;
  }

  @Autowired
  public void setExpenseService(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @Autowired
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    loadProducts();
    loadUsers();
    loadRoles();
    loadCategories();
    loadExpenses();
    assignUsersToUserRole();
    assignUsersToAdminRole();
    assignExpensesToUsers();
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
    User user1 = new User();
    user1.setUsername("user");
    user1.setPassword("user");
    userService.save(user1);

    User user2 = new User();
    user2.setUsername("admin");
    user2.setPassword("admin");
    userService.save(user2);

  }

  private void loadRoles() {
    Role role = new Role();
    role.setRole("USER");
    roleService.save(role);
    log.info("Saved role" + role.getRole());
    Role adminRole = new Role();
    adminRole.setRole("ADMIN");
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
    Category category1 = categoryService.findByName("Food");

    Calendar cal1 = GregorianCalendar.getInstance();
    cal1.set(2018, Month.MAY.getValue() - 1, 1);
    Expense expense1 = new Expense();
    expense1.setAmount(2000.0);
    expense1.setDate(cal1.getTime());
    expense1.setDescription("Milk");
    expense1.setCategory(category1);
    expenseService.save(expense1);

    cal1.set(2018, Month.MAY.getValue() - 1, 31);
    Category category2 = categoryService.findByName("Food");
    Expense expense2 = new Expense();
    expense2.setAmount(6000.0);
    expense2.setDate(cal1.getTime());
    expense2.setDescription("Meat");
    expense2.setCategory(category2);
    expenseService.save(expense2);

    cal1.set(2018, Month.MAY.getValue() - 1, 31);
    Category category3 = categoryService.findByName("Food");
    Expense expense3 = new Expense();
    expense3.setAmount(3000.0);
    expense3.setDate(cal1.getTime());
    expense3.setDescription("Bread");
    expense3.setCategory(category3);
    expenseService.save(expense3);

    cal1.set(2018, Month.MAY.getValue() - 1, 31);
    Category category4 = categoryService.findByName("Electronics");
    Expense expense4 = new Expense();
    expense4.setAmount(6000.0);
    expense4.setDate(cal1.getTime());
    expense4.setDescription("TV");
    expense4.setCategory(category4);
    expenseService.save(expense4);

    cal1.set(2018, Month.MAY.getValue() - 1, 31);
    Category category5 = categoryService.findByName("Medicine");
    Expense expense5 = new Expense();
    expense5.setAmount(1500.0);
    expense5.setDate(cal1.getTime());
    expense5.setDescription("Pills");
    expense5.setCategory(category5);
    expenseService.save(expense5);



    cal1.set(2018, Month.JUNE.getValue() - 1, 1);
    Category category6 = categoryService.findByName("Medicine");
    Expense expense6 = new Expense();
    expense6.setAmount(500.0);
    expense6.setDate(cal1.getTime());
    expense6.setDescription("Expense 3 description");
    expense6.setCategory(category6);
    expenseService.save(expense6);

    cal1.set(2018, Month.APRIL.getValue() - 1, 30);
    Category category7 = categoryService.findByName("Utilities");
    Expense expense7 = new Expense();
    expense7.setAmount(7500.0);
    expense7.setDate(cal1.getTime());
    expense7.setDescription("Expense 4 description");
    expense7.setCategory(category7);
    expenseService.save(expense7);
  }

  private void assignExpensesToUsers() {
    User user = (User) userService.listAll().get(0);
    List<Expense> expenses = (List<Expense>) expenseService.listAll();
    user.setExpenses(expenses);
    userService.save(user);
    expenses.forEach(expense -> {
      expense.setUser(user);
      expenseService.save(expense);
    });

  }

  private void assignUsersToUserRole() {
    List<Role> roles = (List<Role>) roleService.listAll();
    List<User> users = (List<User>) userService.listAll();

    roles.forEach(role -> {
      if (role.getRole().equalsIgnoreCase("USER")) {
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
    List<Role> roles = (List<Role>) roleService.listAll();
    List<User> users = (List<User>) userService.listAll();

    roles.forEach(role -> {
      if (role.getRole().equalsIgnoreCase("ADMIN")) {
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
