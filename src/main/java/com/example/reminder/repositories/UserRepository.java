package com.example.reminder.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.reminder.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
}
