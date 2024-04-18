package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User createUser(AppUserRequest appUserRequest);

    List<User> getAllUser();
    void verifyAccount(String otpCode);
    User findUserByEmail(String email);
    User findUserById(UUID id);

}
