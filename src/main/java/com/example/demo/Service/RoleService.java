package com.example.demo.Service;


import com.example.demo.Repository.RoleRepository;
import com.example.demo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String name) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Role already exists");

        }
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

}
