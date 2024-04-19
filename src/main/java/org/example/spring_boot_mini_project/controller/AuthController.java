package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.spring_boot_mini_project.exception.AccountNotVerifiedException;
import org.example.spring_boot_mini_project.exception.EmailSendingException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.AuthRequest;
import org.example.spring_boot_mini_project.model.dto.request.OtpRequest;
import org.example.spring_boot_mini_project.model.dto.request.PasswordRequest;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.AuthResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.security.JwtService;
import org.example.spring_boot_mini_project.service.OtpService;
import org.example.spring_boot_mini_project.service.ServiceImp.EmailService;
import org.example.spring_boot_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final EmailService emailService;
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, OtpService otpService, EmailService emailService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.emailService = emailService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AppUserRequest appUserRequest) throws PasswordException {

        User user= userService.createUser(appUserRequest);
        UserResponse userResponse =new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setProfileImage(user.getProfileImage());

        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/login")
     public ResponseEntity<?> authentication(@Valid @RequestBody AuthRequest authRequest) throws AccountNotVerifiedException, BadRequestException {
        authenticate(authRequest.getEmail(),authRequest.getPassword());
        final UserDetails userDetails= userService.loadUserByUsername(authRequest.getEmail());
        final String token= jwtService.generateToken(userDetails);
        AuthResponse authResponse=new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    private void authenticate(String email, String password) throws BadRequestException {
        UserDetails userDetails= userService.loadUserByUsername(email);
        User user = userService.findUserByEmail(email);
        System.out.println("user"+user.getUserId());
        Otp otp = otpService.getOtpByUserId(user.getUserId());
        System.out.println("otp"+otp);
        if (otp==null) {
            throw new AccountNotVerifiedException("Invalid");
        } else if(!otp.isVerify()){
            throw new AccountNotVerifiedException("Your account is not verify yet");
        }
        if(userDetails==null)
        {
            throw new AccountNotVerifiedException("Wrong email");
        }

       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

    }
    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String otpCode) {
        userService.verifyAccount(otpCode);
        return ResponseEntity.ok("Your account is verify successful");
    }
    @PostMapping("/resend")
    public ResponseEntity<?> resendCode(@Valid @RequestParam String email) throws MessagingException {
        User user=userService.findUserByEmail(email);
        OtpRequest otp= otpService.generateOtp();
        if(user!=null)
        {
                emailService.sendOtpEmail(user.getEmail(), "OTP", String.valueOf(otp.getOtpCode()));
                otpService.updateOtpcodeAfterResend(otp,user.getUserId());
        }
       else
            throw new EmailSendingException("Invalid email");

        return ResponseEntity.ok("Resend otp code successful");
    }
    @PutMapping("/forget")
    public ResponseEntity <?> forgetPassword(@Valid @RequestBody PasswordRequest passwordRequest,@Valid @RequestParam String email) throws PasswordException {
        System.out.println("pwd"+passwordRequest);
        userService.newPassword(passwordRequest ,email);
        return  ResponseEntity.ok("Your password is reset successful");
    }

}
