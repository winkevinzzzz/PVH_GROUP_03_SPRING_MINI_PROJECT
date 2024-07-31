package org.example.spring_boot_mini_project.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.spring_boot_mini_project.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private Integer otpCode ;
    private LocalDateTime issuedAt;
    private LocalDateTime expiration;
    private Boolean verify;
    private UUID user ;



}
