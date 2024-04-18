package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.example.spring_boot_mini_project.repository.OtpRepository;
import org.example.spring_boot_mini_project.service.OtpService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private static final int OTP_LENGTH = 6; // Adjust length as needed

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public OtpRequest generateOtp() {
        OtpRequest otpRequest = new OtpRequest();
        StringBuilder otpBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            int digit = random.nextInt(10);
            otpBuilder.append(digit);
        }
        otpRequest.setIssuedAt(LocalDateTime.now());
        Integer otpCode = Integer.valueOf(otpBuilder.toString());
        otpRequest.setOtpCode(otpCode);
        otpRequest.setExpiration(LocalDateTime.now().plusMinutes(5));
        return otpRequest;
    }

    @Override
    public void insert(OtpRequest otpRequest) {
        otpRepository.insertOtp(otpRequest);
    }

    @Override
    public void updateOtpcodeAfterResend(OtpRequest otpRequest, UUID userId) {
        otpRepository.updateOtpCodeAfterResend(otpRequest,userId);
    }

}
