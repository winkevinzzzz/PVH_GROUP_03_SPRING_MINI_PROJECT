package org.example.spring_boot_mini_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequest {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least one digit, one letter, and one special character")
    @NotBlank(message = "Password can't be blank")
    private String password;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must contain at least one digit, one letter, and one special character")
    @NotBlank(message = "Password can't be blank")
    private String confirmPassword;
}
