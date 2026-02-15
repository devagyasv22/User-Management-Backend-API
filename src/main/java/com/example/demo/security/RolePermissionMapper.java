package com.example.demo.security;


import java.util.Set;

public class RolePermissionMapper {
    public static Set<Permission> getPermissions(String role) {

        return switch (role) {
            case "ROLE_ADMIN" -> Set.of(
                    Permission.USER_READ_ALL,
                    Permission.USER_UPDATE_ALL,
                    Permission.USER_DELETE_ALL
            );

            case "ROLE_USER" -> Set.of(
                    Permission.USER_READ_SELF,
                    Permission.USER_UPDATE_SELF,
                    Permission.USER_DELETE_SELF
            );

            default -> Set.of();
        };
    }
}
