package org.example.spring_boot_mini_project.service;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.PasswordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User createUser(AppUserRequest appUserRequest);
    List<User> getAllUser();

    User findByEmail(String email);

    User findUserById(UUID userId);

    void verifyAccount(String otpCode);
    User findUserByEmail(String email);

    void resendOtpCode(String email) throws FindNotFoundException, MessagingException;

    void newPassword(@Valid PasswordRequest passwordRequest, String email) throws PasswordException;
}
