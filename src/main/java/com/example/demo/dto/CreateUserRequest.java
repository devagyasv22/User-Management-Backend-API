package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {

    @Email
    @NotBlank
    private String email;

    @Size(min = 6)
    private String password;

    @NotBlank
    private String fullName;

    @NotBlank
    private String phone;

    private String bio;
}
