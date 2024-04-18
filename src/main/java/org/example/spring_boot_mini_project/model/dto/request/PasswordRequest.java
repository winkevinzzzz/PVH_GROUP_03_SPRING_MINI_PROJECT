package org.example.spring_boot_mini_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    private BCryptPasswordEncoder password;
    private BCryptPasswordEncoder confirmPassword;
}
