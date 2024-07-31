package org.example.spring_boot_mini_project.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRequest {
    @Email
    @NotBlank(message = "Email can't be blank")
    private String email;
    @NotBlank(message = "Password can't be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,}$", message = "Password must contain at least one digit, one letter, and one special character")
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "(?i)(.*\\.(jpg|png|gif|bmp))$", message = "Profile image must be jpg, png, gif, or bmp format.")
    private String profileImage;

}
