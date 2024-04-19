package org.example.spring_boot_mini_project.service;

import jakarta.mail.MessagingException;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.PasswordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User createUser(AppUserRequest appUserRequest) throws PasswordException;

    List<User> getAllUser();
    void verifyAccount(String otpCode);
    User findUserByEmail(String email);
    User findUserById(UUID id);

    void resendOtpCode(String email) throws FindNotFoundException, MessagingException;

    void newPassword(PasswordRequest passwordRequest, String email) throws PasswordException;

}
