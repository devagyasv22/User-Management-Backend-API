package com.example.demo.Controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.UserStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody @Valid CreateUserRequest request) {
        return userService.signup(request);
    }

    @GetMapping("/get-all")
    public List<?> getAllUsers(){
        return  userService.getAll();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }

    @PutMapping("/users/{id}/profile")
    public UserResponse updateProfile(@PathVariable Long id,
                                      @RequestBody @Valid UpdateUserRequest request){
        return userService.updateUserProfile(id, request);
    }

    @PutMapping("/users/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);
    }

    @PostMapping("/auth/reset-password")
    public void resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
    }

}
