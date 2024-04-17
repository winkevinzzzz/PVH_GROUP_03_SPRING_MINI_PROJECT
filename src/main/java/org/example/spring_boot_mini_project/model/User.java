package org.example.spring_boot_mini_project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private UUID userId;
    @Email
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String confirmPassword;
    private String profileImage;
}
