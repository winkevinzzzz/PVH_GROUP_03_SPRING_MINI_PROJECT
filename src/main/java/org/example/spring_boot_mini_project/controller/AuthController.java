package org.example.spring_boot_mini_project.controller;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.spring_boot_mini_project.exception.AccountNotVerifiedException;
import org.example.spring_boot_mini_project.exception.FindNotFoundException;
import org.example.spring_boot_mini_project.exception.PasswordException;
import org.example.spring_boot_mini_project.model.Otp;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.AuthRequest;
import org.example.spring_boot_mini_project.model.dto.request.PasswordRequest;
import org.example.spring_boot_mini_project.model.dto.response.AuthResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.repository.OtpRepository;
import org.example.spring_boot_mini_project.security.JwtService;
import org.example.spring_boot_mini_project.service.FileService;
import org.example.spring_boot_mini_project.service.OtpService;
import org.example.spring_boot_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final FileService fileService;
    private final OtpService otpService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, FileService fileService, OtpRepository otpRepository, OtpService otpService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.fileService = fileService;
        this.otpService = otpService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AppUserRequest appUserRequest)
    {

        User user= userService.createUser(appUserRequest);
        UserResponse userResponse =new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setProfileImage(user.getProfileImage());

        return ResponseEntity.ok(userResponse);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authentication(@Valid @RequestBody AuthRequest authRequest) throws BadRequestException, PasswordException {
        authenticate(authRequest.getEmail(),authRequest.getPassword());
        final UserDetails userDetails= userService.loadUserByUsername(authRequest.getEmail());
        final String token= jwtService.generateToken(userDetails);
        AuthResponse authResponse=new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    private void authenticate(String email, String password) throws AccountNotVerifiedException, PasswordException {

        UserDetails userDetails= userService.loadUserByUsername(email);
        User user = userService.findUserByEmail(email);
        if(user==null)
        {
            throw new AccountNotVerifiedException("Wrong email");
        }
        if(!bCryptPasswordEncoder.matches(password,userDetails.getPassword())){
            throw new PasswordException("Wrong password");
        }
        Otp otp = otpService.getOtpByUserId(user.getUserId());
        if(!otp.isVerify()){
            throw new AccountNotVerifiedException("Your account is not verify yet");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
    }
    @PostMapping("/resend")
    public ResponseEntity <?> resendOtpCode(@RequestParam String email) throws FindNotFoundException {
        userService.resendOtpCode(email);
        return new ResponseEntity<>("Resend otp code successful",HttpStatus.OK);
    }
    @PutMapping("/forget")
    public ResponseEntity <?> forgetPassword(@RequestBody PasswordRequest passwordRequest, @RequestParam String email) throws PasswordException {
       userService.newPassword(passwordRequest ,email);
        return new ResponseEntity<>("Your password is reset successful", HttpStatus.OK);
    }
    @PutMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String otpCode) {
        userService.verifyAccount(otpCode);
        return ResponseEntity.ok("Your account is verify successful");
    }

}
