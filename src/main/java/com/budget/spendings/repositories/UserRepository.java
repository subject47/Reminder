package com.budget.spendings.repositories;

import org.springframework.data.repository.CrudRepository;

import com.budget.spendings.domain.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByUsername(String username);
}
