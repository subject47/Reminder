package com.example.reminder.services;

import com.example.reminder.domain.User;

public interface UserService extends CRUDService<User> {
	User findByUsername(String username);
}
