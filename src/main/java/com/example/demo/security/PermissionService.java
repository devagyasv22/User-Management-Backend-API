package com.example.demo.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class PermissionService {
    public boolean hasPermission(Authentication auth, Permission permission) {

        if (auth == null || auth.getAuthorities() == null) return false;

        return auth.getAuthorities().stream()
                .map(a -> RolePermissionMapper.getPermissions(a.getAuthority()))
                .flatMap(Set::stream)
                .anyMatch(p -> p == permission);
    }
}
