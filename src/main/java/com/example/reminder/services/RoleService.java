package com.example.reminder.services;

import com.example.reminder.domain.Role;
import com.example.reminder.repositories.RoleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements CRUDService<Role> {

    private static final Logger log = Logger.getLogger(RoleService.class.getName());

    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> listAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }

    @Override
    public Role getById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }


    public Role getByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        log.info(() -> "Saving role: " + role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
