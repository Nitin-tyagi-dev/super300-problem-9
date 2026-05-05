
package com.cdrn.dto;

import com.cdrn.model.Enums;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class AuthDtos {
    @Data
    public static class SendOtpRequest {
        @NotBlank
        private String phone;
    }

    @Data
    public static class VerifyOtpRequest {
        @NotBlank
        private String phone;
        @NotBlank
        private String otp;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank
        private String name;
        @NotBlank
        private String phone;
        @Email
        private String email;
        @NotBlank
        private String password;
        @NotNull
        private Enums.Role role;
        @NotBlank
        private String district;
        @NotBlank
        private String state;
        private String aadhaarNumber;
    }

    @Data
    public static class LoginRequest {
        @NotBlank
        private String phone;
        @NotBlank
        private String password;
    }
}
