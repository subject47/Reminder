package com.example.reminder.services;

import com.example.reminder.domain.Category;
import com.example.reminder.domain.Expense;
import com.example.reminder.repositories.ExpenseRepository;
import com.example.reminder.utils.DateUtils;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService implements CRUDService<Expense> {

  private final ExpenseRepository expenseRepository;
  private final CategoryService categoryService;

  @Autowired
  public ExpenseService(ExpenseRepository expenseRepository, CategoryService categoryService) {
    this.expenseRepository = expenseRepository;
    this.categoryService = categoryService;
  }

  public List<Expense> listAll() {
    List<Expense> expenses = new ArrayList<>();
    expenseRepository.findAll().forEach(expenses::add);
    return expenses;
  }

  public Expense getById(Integer id) {
    return expenseRepository.findById(id).orElse(null);
  }

  public Expense save(Expense expense) {
    return expenseRepository.save(expense);
  }

  public void delete(Integer id) {
    expenseRepository.deleteById(id);
  }

  public List<Expense> findAllExpensesByYearAndMonth(Year year, Month month) {
    LocalDate start = LocalDate.of(year.getValue(), month, 1);
    LocalDate end = start.plusMonths(1).minusDays(1);
    return expenseRepository.findByDateBetween(DateUtils.asDate(start), DateUtils.asDate(end));
  }

  public List<Expense> findExpensesByYearAndMonthAndUsername(Year year, Month month, String username) {
    return findAllExpensesByYearAndMonth(year, month)
        .stream()
        .filter(e -> e.getUser().getUsername().equals(username))
        .sorted(Comparator.comparing(Expense::getDate))
        .collect(Collectors.toList());
  }

  public void buildDatagrid(Year year, Month month, String username) {
    List<Expense> expenses = findExpensesByYearAndMonthAndUsername(year, month, username);
    List<List<Object>> grid = Lists.newArrayListWithCapacity(month.length(year.isLeap()));
    List<String> header = buildDataGridHeader(categoryService.listAll());

  }

  private static List<String> buildDataGridHeader(List<Category> categories) {
    AtomicInteger counter = new AtomicInteger(0);
    Map<String, Integer> categoriesMap =
        categories.stream()
            .sorted()
            .map(c -> ImmutablePair.of(c.getName(), counter.incrementAndGet()))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

    int rowLength = categories.size() + 4;
    List<String> header = Lists.newArrayListWithCapacity(rowLength);
    header.add(0, "");  // expenseId hidden column
    header.add(1, "");  // day of month column
    header.add(2, "");  // footer label column
    header.addAll(categoriesMap.keySet().stream()
        .sorted()
        .collect(Collectors.toList()));
    header.add("Total");
    return header;
  }
}
