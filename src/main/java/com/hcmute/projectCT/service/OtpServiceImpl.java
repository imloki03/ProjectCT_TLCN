package com.hcmute.projectCT.service;

import com.hcmute.projectCT.util.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{
    final EmailUtil emailUtil;
    private final Map<String, String> otpStorage = new HashMap<>();  //use redis instead map
    @Override
    public String generateOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        return otp;
    }

    @Override
    public void sendOtp(String email) throws MessagingException, IOException {
        String otp = generateOtp(email);
        String subject = "OTP Verification";
        emailUtil.sendEmail(email, subject, "otp-template.html", otp);
    }

    @Override
    public boolean verifyOTP(String email, String otp) {
        String storedOtp = otpStorage.get(email);
        log.error(otpStorage.toString());
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }
}
