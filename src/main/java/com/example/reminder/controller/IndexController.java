package com.example.reminder.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.reminder.domain.User;
import com.example.reminder.repositories.UserRepository;
import com.example.reminder.services.RoleService;
import com.example.reminder.services.UserService;
import com.google.common.collect.Lists;

@Controller
public class IndexController {

  @Autowired
  private UserService userService;
  
  @Autowired
  private RoleService roleService;
	
  @GetMapping("/")
  public String index() {
    return "index";
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
	  user.setEnabled(true);
	  user.setRoles(Lists.newArrayList(roleService.getById(1)));
	  userService.save(user);
	  return "redirect:/login";
  }

}
