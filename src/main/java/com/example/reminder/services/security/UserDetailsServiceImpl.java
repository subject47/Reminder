package com.example.reminder.services.security;

import com.example.reminder.domain.User;
import com.example.reminder.services.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserService userService;
  private Converter<User, UserDetails> userUserDetailsConverter;

  public UserDetailsServiceImpl(UserService userService, Converter<User, UserDetails> userUserDetailsConverter) {
    this.userService = userService;
    this.userUserDetailsConverter = userUserDetailsConverter;
  }

  @Override
  public UserDetails loadUserByUsername(String username) {
    return userUserDetailsConverter.convert(userService.findByUsername(username));
  }
}
