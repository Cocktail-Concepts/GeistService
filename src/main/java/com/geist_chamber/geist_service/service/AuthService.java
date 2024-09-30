package com.geist_chamber.geist_service.service;

import com.geist_chamber.geist_service.constant.Potency;
import com.geist_chamber.geist_service.dto.GeistSignUpDto;
import com.geist_chamber.geist_service.entity.Geist;
import com.geist_chamber.geist_service.exception.RestError;
import com.geist_chamber.geist_service.repository.GeistRepository;
import com.geist_chamber.geist_service.repository.VocationRepository;
import com.geist_chamber.geist_service.security.JwtUtil;
import com.geist_chamber.geist_service.utilities.EncryptionUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;


    public AuthService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, GeistRepository geistRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender, EncryptionUtil encryptionUtil, RedisTemplate redisTemplate,
                       VocationRepository vocationRepository) {
        this.jwtUtil = jwtUtil;
        this.geistRepository = geistRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.encryptionUtil = encryptionUtil;
        this.redisTemplate = redisTemplate;
        this.vocationRepository = vocationRepository;
    }

    private final GeistRepository geistRepository;


    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;
    private final EncryptionUtil encryptionUtil;
    private final RedisTemplate redisTemplate;
    private final VocationRepository vocationRepository;

    public void signUp(GeistSignUpDto geistSignUpDTO) {
        String encEmail = encryptionUtil.encrypt(geistSignUpDTO.getEmail().toLowerCase().trim());
        Geist geistEx = geistRepository.findByEmail(geistSignUpDTO.getEmail());
        if (geistEx != null) throw new RestError(HttpStatus.CONFLICT, "user already exists for this mail address");
        Geist geist = new Geist();
        geist.setEmail(geistSignUpDTO.getEmail());
        geist.setGeistname(encEmail);
        geist.setPotentiates(Collections.singleton(Potency.COMPANION));
        geist.setName(extractNameFromEmail(geistSignUpDTO.getEmail()));
        geist.setPassword(passwordEncoder.encode(geistSignUpDTO.getPassword()));
        geistRepository.save(geist);
        sendEmailOtp(geistSignUpDTO.getEmail());

    }

    public String login(GeistSignUpDto loginRequest) {
        Geist geist = geistRepository.findByEmail(loginRequest.getEmail());
        if (geist == null) throw new RestError(HttpStatus.NOT_FOUND, "user not found");
        if (passwordEncoder.matches(loginRequest.getPassword(), geist.getPassword()))
            return jwtUtil.generateToken(geist);
        else throw new RestError(HttpStatus.UNAUTHORIZED, "wrong password.try again or reset password");
    }

    private String extractNameFromEmail(String email) {
        String localPart = email.split("@")[0];
        return localPart.replaceAll("[^a-zA-Z]", " "); // Replace non-alphabet characters with space
    }

    public void sendEmailOtp(String email) {
        String otp = generateOtp();
        redisTemplate.opsForValue().set(email, otp, 15, TimeUnit.MINUTES);
        sendEmail(email, "Your OTP Code", "Your OTP is " + otp);
    }

    public String verifyOtp(String email, String otp) {
        Geist geist = geistRepository.findByEmail(email);
        if (geist == null) throw new RestError(HttpStatus.NOT_FOUND, "user not found");
        String cachedOtp = (String) redisTemplate.opsForValue().get(email);
        if (cachedOtp == null) throw new RestError(HttpStatus.NOT_FOUND, "otp not found");
        String token = null;
        if (cachedOtp.equals(otp)) {
            token = jwtUtil.generateToken(geist);
            geist.setEmailVerified(true);
            geistRepository.save(geist);
        }
        return token;
    }

    public void forgotPassword(String email) {
        Geist geist = geistRepository.findByGeistname(encryptionUtil.encrypt(email));
        if (geist == null) throw new RestError(HttpStatus.NOT_FOUND, "user not found");
        sendEmailOtp(geist.getEmail());
    }

    public void resetPassword(String email, String otp, String newPassword) {
        String token = verifyOtp(email, otp);
        if (token != null) {
            Geist geist = geistRepository.findByGeistname(encryptionUtil.encrypt(email));
            if (geist == null) throw new RestError(HttpStatus.NOT_FOUND, "user not found");
            geist.setPassword(passwordEncoder.encode(newPassword));
            geistRepository.save(geist);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }

    private String generateOtp() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
