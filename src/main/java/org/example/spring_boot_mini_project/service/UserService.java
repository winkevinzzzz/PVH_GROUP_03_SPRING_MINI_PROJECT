package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User createUser(AppUserRequest appUserRequest);
    List<User> getAllUser();
}
