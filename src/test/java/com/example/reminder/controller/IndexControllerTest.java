package com.example.reminder.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.reminder.domain.Role;
import com.example.reminder.domain.User;
import com.example.reminder.services.RoleService;
import com.example.reminder.services.UserService;
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
public class IndexControllerTest {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String REPEAT_PASSWORD = "repeatPassword";

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @MockBean
  private RoleService roleService;

  @Test
  void index() throws Exception {
    mvc.perform(get("/index").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"));
  }

  @Test
  void login() throws Exception {
    mvc.perform(get("/login").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("login"));
  }

  @Test
  void registration() throws Exception {
    mvc.perform(get("/registration").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("registration"));
  }

  @Test
  void registration_registerNewUser() throws Exception {
    when(roleService.getByName(Role.USER)).thenReturn(new Role());

    mvc.perform(post("/registration")
        .param(USERNAME, "username")
        .param(PASSWORD, "password")
        .param(REPEAT_PASSWORD, "password").with(csrf()))
        .andDo(print())
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/login"));

    verify(roleService).getByName(Role.USER);
    verify(userService).save(any(User.class));
  }

  @Test
  void registration_userExist() throws Exception {
    when(userService.findByUsername("username")).thenReturn(new User());

    mvc.perform(post("/registration")
        .param(USERNAME, "username")
        .param(PASSWORD, "password")
        .param(REPEAT_PASSWORD, "password").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attribute("message", "User exists!"))
        .andExpect(view().name("/registration"));

    verify(roleService, never()).getByName(anyString());
    verify(userService, never()).save(any(User.class));
  }

  @Test
  void registration_passwordTooShort() throws Exception {
    mvc.perform(post("/registration")
        .param(USERNAME, "username")
        .param(PASSWORD, "p")
        .param(REPEAT_PASSWORD, "password").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attribute("message", "Password must contain at least 3 characters."))
        .andExpect(view().name("/registration"));

    verify(roleService, never()).getByName(anyString());
    verify(userService, never()).save(any(User.class));
  }

  @Test
  void registration_passwordsDoNotMatch() throws Exception {
    mvc.perform(post("/registration")
        .param(USERNAME, "username")
        .param(PASSWORD, "password")
        .param(REPEAT_PASSWORD, "repeatPassword").with(csrf()))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(model().attribute("message", "Passwords don't match!"))
        .andExpect(view().name("/registration"));

    verify(roleService, never()).getByName(anyString());
    verify(userService, never()).save(any(User.class));
  }
}
