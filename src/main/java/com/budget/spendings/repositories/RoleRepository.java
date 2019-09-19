package com.budget.spendings.repositories;

import org.springframework.data.repository.CrudRepository;

import com.budget.spendings.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

  Role findByName(String name);

}
