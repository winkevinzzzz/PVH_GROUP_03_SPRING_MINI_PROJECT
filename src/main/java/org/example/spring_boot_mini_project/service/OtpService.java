package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;


public interface OtpService {
    OtpRequest generateOtp();
    void insert(OtpRequest otpRequest);
    void updateOtpcodeAfterResend(OtpRequest otp, UUID userId);
}
