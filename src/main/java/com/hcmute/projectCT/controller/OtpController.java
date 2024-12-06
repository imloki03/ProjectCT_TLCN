package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.service.OtpService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/v1/otp")
@RequiredArgsConstructor
@Tag(name = "OTP Controller")
public class OtpController {
    final OtpService otpService;
    final MessageUtil messageUtil;

    @Operation(
            summary = "Send OTP",
            description = "This API will send an OTP to a email.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Send OTP successfully"),
            })
    @PostMapping("/send/{email}")
    public ResponseEntity<?> sendOtp(@PathVariable String email) throws MessagingException, IOException {
        otpService.sendOtp(email);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @Operation(
            summary = "Verify OTP",
            description = "This API will verify OTP user sent.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Verify OTP successfully"),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Verify OTP failed"),
            })
    @PostMapping("verify/{email}/{otp}")
    public ResponseEntity<?> verifyOtp(@PathVariable String email, @PathVariable String otp) {
        if (otpService.verifyOTP(email, otp)) {
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } else {
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.FORBIDDEN.value())
                    .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        }
    }
}
