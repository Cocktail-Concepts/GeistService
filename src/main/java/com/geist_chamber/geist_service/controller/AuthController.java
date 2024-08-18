package com.geist_chamber.geist_service.controller;

import com.geist_chamber.geist_service.dto.GeistSignUpDto;
import com.geist_chamber.geist_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController extends AbstractUtilController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody GeistSignUpDto geist) {
        authService.signUp(geist);
        return successResponse("User registered successfully. Check your email for OTP.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        String token = authService.verifyOtp(email, otp);
        System.out.println("tokenG");
        if (token != null) {
            return singleResponse(token);
        } else {
            return errorResponse("Invalid OTP");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        authService.forgotPassword(email);
        return successResponse("OTP sent to your email.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String otp, @RequestParam String newPassword) {
        try {
            authService.resetPassword(email, otp, newPassword);
            return successResponse("Password reset successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
