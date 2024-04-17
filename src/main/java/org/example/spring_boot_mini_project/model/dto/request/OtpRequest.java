package org.example.spring_boot_mini_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.spring_boot_mini_project.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String otpCode ;
    private LocalDateTime issuedAt;
    private LocalDateTime expiration;
    User user ;



}
