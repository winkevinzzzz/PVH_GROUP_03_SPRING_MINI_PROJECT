package org.example.spring_boot_mini_project.service.ServiceImp;

import jakarta.mail.MessagingException;
import org.example.spring_boot_mini_project.exception.EmailSendingException;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.CustomUserDetail;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.example.spring_boot_mini_project.repository.UserRepository;
import org.example.spring_boot_mini_project.service.OtpService;
import org.example.spring_boot_mini_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;
    private final OtpService otpService;
    private static final int OTP_LENGTH = 6;
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, EmailService emailService, OtpService otpService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.emailService = emailService;
        this.otpService = otpService;
    }
    @Override
    public User createUser(AppUserRequest appUserRequest) throws EmailSendingException {

        // Check for existing email
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(appUserRequest.getEmail()));
        if (existingUser.isPresent()) {
            throw new EmailSendingException("Email address already exists");
        }
        OtpRequest otp= otpService.generateOtp();

        try {
            emailService.sendOtpEmail(appUserRequest.getEmail(), "OTP", String.valueOf(otp.getOtpCode()));
        }
        catch (Exception e) {

            throw new RuntimeException("Unexpected error during user creation", e);
        }
        //encoded Password
        String encodedPassword = encoder.encode(appUserRequest.getPassword());
        appUserRequest.setPassword(encodedPassword);
        User user= userRepository.insert(appUserRequest);
        otp.setUser(user.getUserId());
        otp.setVerify(false);
         otpService.insert(otp);

       // return userRepository.insert(appUserRequest);
       return modelMapper.map(user,User.class);
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

//    @Override
//    public void resendOtpCode(String email) throws FindNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null){
//            throw new FindNotFoundException("Cannot find your email account please register first");
//        }
//        OtpRequest otp= otpService.generateOtp();
//        emailService.sendOtpEmail(email,"OTP",user,otp.toString();
//        otpService.updateThecodeAfterResend(otp,user.getUserId());
//
//    }
@Override
public void resendOtpCode(String email) throws FindNotFoundException {
    User user = userRepository.findByEmail(email);
    if (user == null) {
        throw new FindNotFoundException("Cannot find your email account please register first");
    }
    Integer otpCode = otpService.generateOtp().getOtpCode(); // Get the OTP code
    OtpRequest otpRequest = new OtpRequest();
    otpRequest.setIssuedAt(LocalDateTime.now());
    otpRequest.setOtpCode(otpCode);
    otpRequest.setExpiration(LocalDateTime.now().plusMinutes(5));
    emailService.sendOtpEmail(email, "OTP", otpCode.toString());
    otpService.updateOtpcodeAfterResend(otpRequest, user.getUserId());
}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new CustomUserDetail(user);

    }
}
