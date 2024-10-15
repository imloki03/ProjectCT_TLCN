package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.dto.User.RegisterRequest;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.exception.RegistrationException;
import com.hcmute.projectCT.service.UserService;
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
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MessageUtil messageUtil;

    @PostMapping("")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
//            emailService.sendOtpEmail(registerRequest.getEmail(), EmailService.generateOtp());
            var respondData = RespondData
                    .builder()
                    .status(HttpStatus.OK.value())
                    .desc(messageUtil.getMessage(MessageKey.REGISTER_SUCCESS))
                    .build();

            return new ResponseEntity<>(respondData, HttpStatus.OK);
        } catch (RegistrationException e){
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.BAD_REQUEST);
        } catch (InternalServerException e) {
            var respondData = RespondData
                    .builder()
                    .status(e.getErrorCode())
                    .desc(messageUtil.getMessage(e.getMessage()))
                    .build();
            return new ResponseEntity<>(respondData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
