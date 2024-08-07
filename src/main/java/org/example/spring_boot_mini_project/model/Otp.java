package org.example.spring_boot_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp {
   private UUID otpId ;
   private Integer otpCode ;
   private LocalDateTime issuedAt;
   private LocalDateTime expiration;
   private boolean verify;
   private User userId ;
}
