package org.example.spring_boot_mini_project.service.ServiceImp;

import org.example.spring_boot_mini_project.exception.AccountNotVerifiedException;
import org.example.spring_boot_mini_project.exception.EmailSendingException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.CustomUserDetail;
import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.example.spring_boot_mini_project.model.dto.request.PasswordRequest;
import org.example.spring_boot_mini_project.repository.UserRepository;
import org.example.spring_boot_mini_project.service.OtpService;
import org.example.spring_boot_mini_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    private  final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final EmailService emailService;
    private final OtpService otpService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, EmailService emailService, OtpService otpService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.emailService = emailService;
        this.otpService = otpService;
    }


    @Override
    public User createUser(AppUserRequest appUserRequest) throws EmailSendingException, PasswordException {
        if(!appUserRequest.getPassword().equals(appUserRequest.getConfirmPassword())){
            throw new PasswordException("Password must be the same as confirm password");
        }

        // Check for existing email
        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(appUserRequest.getEmail()));
        if (existingUser.isPresent()) {
            throw new EmailSendingException("Email address already exists");
        }
        OtpRequest otp= otpService.generateOtp();

        try {
            emailService.sendOtpEmail(appUserRequest.getEmail(), "Verify OTP code", String.valueOf(otp.getOtpCode()));
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

       //return modelMapper.map(user,User.class);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public void verifyAccount(String otpCode) {
        Otp otp = otpService.getOtpCode(otpCode);
        if(otp == null){
            throw new AccountNotVerifiedException("invalid code");
        }else {
            if(otp.isVerify()){
                throw new AccountNotVerifiedException("has been verified you can login");
            } else if (otp.getExpiration().isBefore(LocalDateTime.now())) {
                throw new AccountNotVerifiedException("Opt code is expired ");
            }else {
                otp.setVerify(true);
                otpService.updateVerifyAfterVerified(otp);
                System.out.println(otp);
            }
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public void newPassword(PasswordRequest passwordRequest, String email) throws PasswordException {
        if (!passwordRequest.getPassword().equals(passwordRequest.getConfirmPassword())){
            throw new PasswordException("Your Password is not match ");
        }
        if(userRepository.findByEmail(email)!= null){
            String passwordEncode = encoder.encode(passwordRequest.getPassword());
            passwordRequest.setPassword(passwordEncode);
            userRepository.newPassword(passwordRequest,email);

        }
        else
            throw new PasswordException("Invalid email");
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        return new CustomUserDetail(user);

    }
}
