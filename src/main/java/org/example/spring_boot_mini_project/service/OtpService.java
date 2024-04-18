package org.example.spring_boot_mini_project.service;

import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.springframework.stereotype.Service;

import java.util.Random;


public interface OtpService {
    OtpRequest generateOtp();
    void insert(OtpRequest otpRequest);


}
