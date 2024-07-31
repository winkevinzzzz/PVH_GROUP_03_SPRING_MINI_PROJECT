package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;


public interface OtpService {
    OtpRequest generateOtp();
    void insert(OtpRequest otpRequest);
    Otp getOtpCode(String code);
    void updateVerifyAfterVerified(Otp otp);

    Otp getOtpByUserId(UUID userId);
    void updateResendCode(OtpRequest otpRequest, UUID userId);
}
