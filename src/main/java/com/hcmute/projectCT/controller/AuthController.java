package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.User.LoginRequest;
import com.hcmute.projectCT.exception.LoginFailedException;
import com.hcmute.projectCT.service.AuthService;
import com.hcmute.projectCT.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    final AuthService authService;
    final MessageUtil messageUtil;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            authService.login(loginRequest.getUsername(), loginRequest.getPassword());
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.LOGIN_SUCCESS))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (LoginFailedException e){
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.UNAUTHORIZED);
        }
    }
}
