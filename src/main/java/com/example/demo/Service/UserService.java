package com.example.demo.Service;

import com.example.demo.Repository.RoleRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.*;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.UserStatus;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.security.Permission;
import com.example.demo.security.PermissionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    @Transactional
    public UserResponse signup(CreateUserRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setStatus(UserStatus.ACTIVE);

        // roles (collection is already initialized)
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        user.getRoles().add(userRole);
        // profile
        UserProfile profile = new UserProfile();
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setBio(request.getBio());

        // BOTH SIDES MUST BE SET
        profile.setUser(user);
        user.setProfile(profile);

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getStatus()
        );
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password must be different");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void  deleteById(Long id){
        userRepository.deleteById(id);
    }

    public UserResponse updateUserProfile(Long id, UpdateUserRequest request) {

        User user = getById(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = auth.getName(); // JWT subject = email

        boolean canUpdateAll =
                permissionService.hasPermission(auth, Permission.USER_UPDATE_ALL);

        boolean canUpdateSelf =
                permissionService.hasPermission(auth, Permission.USER_UPDATE_SELF);

        // ADMIN → update anyone
        if (canUpdateAll) {
            updateProfile(user, request);
            return toResponse(userRepository.save(user));
        }

        // USER → update own profile only
        if (canUpdateSelf && user.getEmail().equals(loggedInEmail)) {
            updateProfile(user, request);
            return toResponse(userRepository.save(user));
        }

        throw new AccessDeniedException("You are not allowed to update this profile");
    }

    private void updateProfile(User user, UpdateUserRequest request) {
        UserProfile profile = user.getProfile();
        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setBio(request.getBio());
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getStatus()
        );
    }

    public void assignRole(Long userId, String roleName) {

        User user = getById(userId);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        role.getUsers().add(user);

        userRepository.save(user);
    }






}

