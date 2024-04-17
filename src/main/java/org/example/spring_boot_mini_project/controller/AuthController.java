package org.example.spring_boot_mini_project.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.example.spring_boot_mini_project.model.User;
import org.example.spring_boot_mini_project.model.dto.request.AppUserRequest;
import org.example.spring_boot_mini_project.model.dto.request.AuthRequest;
import org.example.spring_boot_mini_project.model.dto.response.ApiResponse;
import org.example.spring_boot_mini_project.model.dto.response.AuthResponse;
import org.example.spring_boot_mini_project.model.dto.response.UserResponse;
import org.example.spring_boot_mini_project.security.JwtService;
import org.example.spring_boot_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
     public ResponseEntity<?> authentication(@RequestBody AuthRequest authRequest) throws BadRequestException {
        authenticate(authRequest.getEmail(),authRequest.getPassword());
        final UserDetails userDetails= userService.loadUserByUsername(authRequest.getEmail());
        final String token= jwtService.generateToken(userDetails);
        AuthResponse authResponse=new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    private void authenticate(String email, String password) throws BadRequestException {
        UserDetails userDetails= userService.loadUserByUsername(email);
        if(userDetails==null)
        {
            throw new BadRequestException("Wrong email");
        }

       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));

    }
}
