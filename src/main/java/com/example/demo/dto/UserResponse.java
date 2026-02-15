package com.example.demo.dto;

import com.example.demo.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private UserStatus status;
}
