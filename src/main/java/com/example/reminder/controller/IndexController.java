package com.example.reminder.controller;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.reminder.domain.User;
import com.example.reminder.services.RoleService;
import com.example.reminder.services.UserService;
import com.google.common.collect.Lists;

@Controller
public class IndexController {

  private static final int PASSWORD_LENGTH_MIN = 3;	
	
  @Autowired
  private UserService userService;
  
  @Autowired
  private RoleService roleService;
	
  @GetMapping("/")
  public String index() {
    return "index";
  }

	@GetMapping(value="/login")
	public String login() {
		return "login";
	}
  
  @GetMapping("/registration")
  public String registration() {
	  return "registration";
  }
  
  @PostMapping("/registration")
  public String registration(User user, Model model) {
	  User existingUser = userService.findByUsername(user.getUsername());
	  if (existingUser != null) {
		  model.addAttribute("message", "User exists!");
		  return "/registration";
	  }
	  if (StringUtils.isEmpty(user.getPassword()) || user.getPassword().length() < PASSWORD_LENGTH_MIN) {
		  model.addAttribute("message", "Password must contain at least " + PASSWORD_LENGTH_MIN + " characters.");
		  return "/registration";
	  }
	  if (StringUtils.isEmpty(user.getRepeatPassword())) {
		  model.addAttribute("message", "Repeat password");
		  return "/registration";
	  }
	  if (!user.getPassword().equals(user.getRepeatPassword())) {
		  model.addAttribute("message", "Passwords don't match!");
		  return "/registration";
	  }
	  user.setEnabled(true);
	  user.setRoles(Lists.newArrayList(roleService.getById(1)));
	  userService.save(user);
	  return "redirect:/login";
  }

}
