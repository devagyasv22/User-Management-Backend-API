package com.example.demo.Controller;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.Service.RoleService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void createRole(@RequestParam String name) {
        roleService.createRole(name);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/assign")
    public void assignRole(
            @RequestParam Long userId,
            @RequestParam String role
    ) {
        userService.assignRole(userId, role);
    }

    @GetMapping("/me")
    public Map<String, Object> me(Authentication authentication) {
        return Map.of(
                "email", authentication.getName(),
                "authorities", authentication.getAuthorities()
        );
    }



}


