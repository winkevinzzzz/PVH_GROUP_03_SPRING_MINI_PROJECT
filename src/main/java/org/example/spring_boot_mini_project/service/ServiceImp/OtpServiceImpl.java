package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.service.OtpService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {


    private static final int OTP_LENGTH = 6; // Adjust length as needed

    public String generateOtp() {
        StringBuilder otpBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int digit = random.nextInt(10);
            otpBuilder.append(digit);
        }
        return otpBuilder.toString();
    }
}
