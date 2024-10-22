package com.hcmute.projectCT.service;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface OtpService {
    String generateOtp(String email);
    void sendOtp(String email) throws MessagingException, IOException;
    boolean verifyOTP(String email, String otp);
}
