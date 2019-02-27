package com.example.reminder.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.reminder.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

  Role findByRole(String name);

}
