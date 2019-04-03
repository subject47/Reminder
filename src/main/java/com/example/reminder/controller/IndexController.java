package com.example.reminder.controller;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import com.example.reminder.domain.Role;
import com.example.reminder.domain.User;
import com.example.reminder.services.RoleService;
import com.example.reminder.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

  private static final String MESSAGE = "message";
  private static final String REGISTRATION_TEMPLATE = "/registration";

  private static final int PASSWORD_LENGTH_MIN = 3;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @GetMapping("/index")
  public String index() {
    return "index";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(User user, Model model) {
    if (userService.findByUsername(user.getUsername()) != null) {
      model.addAttribute(MESSAGE, "User exists!");
      return REGISTRATION_TEMPLATE;
    }
    if (isEmpty(user.getPassword()) || user.getPassword().length() < PASSWORD_LENGTH_MIN) {
      model.addAttribute(MESSAGE, "Password must contain at least " + PASSWORD_LENGTH_MIN + " characters.");
      return REGISTRATION_TEMPLATE;
    }
    if (isEmpty(user.getRepeatPassword())) {
      model.addAttribute(MESSAGE, "Repeat password");
      return REGISTRATION_TEMPLATE;
    }
    if (!user.getPassword().equals(user.getRepeatPassword())) {
      model.addAttribute(MESSAGE, "Passwords don't match!");
      return REGISTRATION_TEMPLATE;
    }
    user.setEnabled(true);
    user.addRole(roleService.getByName(Role.USER));
    userService.save(user);
    return "redirect:/login";
  }

}
