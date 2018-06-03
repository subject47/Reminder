package com.example.reminder.controller;

import java.time.Month;
import java.time.Year;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.reminder.domain.Expense;
import com.example.reminder.forms.DateForm;
import com.example.reminder.services.ExpenseService;

@Controller
public class IndexController {

	private static final Logger logger = Logger.getLogger(IndexController.class);
	
	@Autowired
	private ExpenseService expenseService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/dates")
	public String dates(Model model) {
		model.addAttribute("dateform", new DateForm());
		return "dates";
	}
	
	@RequestMapping(value="/dateSelected", method = RequestMethod.POST)
	public String dateSelected(DateForm dateform, Model model) {
		System.out.println("Here!!");
		logger.info("Selected year: " + dateform.getYear() + "; Selected month: " + dateform.getMonth());
		List<Expense> expenses = expenseService.findAllExpensesForYearAndMonth(
				Year.of(dateform.getYear()),
				Month.of(dateform.getMonth()));
		expenses.stream().forEach(System.out::println);
		return "index";
	}
}
