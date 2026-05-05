package com.cdrn.service;

import com.cdrn.dto.AuthDtos;
import com.cdrn.model.OtpRecord;
import com.cdrn.model.User;
import com.cdrn.repository.OtpRecordRepository;
import com.cdrn.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRecordRepository otpRecordRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            UserRepository userRepository,
            OtpRecordRepository otpRecordRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.otpRecordRepository = otpRecordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Map<String, Object> sendOtp(AuthDtos.SendOtpRequest request) {
        String otp = "123456";
        LocalDateTime now = LocalDateTime.now();
        otpRecordRepository.save(OtpRecord.builder()
                .phone(request.getPhone())
                .otp(otp)
                .used(false)
                .createdAt(now)
                .expiresAt(now.plusMinutes(10))
                .build());

        return Map.of(
                "success", true,
                "message", "OTP sent successfully",
                "devOtp", otp
        );
    }

    public Map<String, Object> verifyOtp(AuthDtos.VerifyOtpRequest request) {
        OtpRecord otp = otpRecordRepository.findTopByPhoneOrderByCreatedAtDesc(request.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("No OTP found for this phone."));

        if (otp.isUsed()) {
            throw new IllegalArgumentException("OTP already used.");
        }
        if (otp.getExpiresAt() != null && otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("OTP expired.");
        }
        if (!otp.getOtp().equals(request.getOtp())) {
            throw new IllegalArgumentException("Invalid OTP.");
        }

        otp.setUsed(true);
        otpRecordRepository.save(otp);

        return Map.of("success", true, "message", "OTP verified");
    }

    public Map<String, Object> register(AuthDtos.RegisterRequest request) {
        userRepository.findByPhone(request.getPhone()).ifPresent(u -> {
            throw new IllegalArgumentException("Phone already registered.");
        });

        User user = User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .district(request.getDistrict())
                .state(request.getState())
                .aadhaarNumber(request.getAadhaarNumber())
                .isVerified(true)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return authResponse(savedUser);
    }

    public Map<String, Object> login(AuthDtos.LoginRequest request) {
        User user = userRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials.");
        }
        return authResponse(user);
    }

    private Map<String, Object> authResponse(User user) {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("id", user.getId());
        userPayload.put("name", user.getName());
        userPayload.put("phone", user.getPhone());
        userPayload.put("email", user.getEmail());
        userPayload.put("role", user.getRole() != null ? user.getRole().name() : null);
        userPayload.put("district", user.getDistrict());
        userPayload.put("state", user.getState());

        return Map.of(
                "success", true,
                "token", UUID.randomUUID().toString(),
                "user", userPayload
        );
    }
}
