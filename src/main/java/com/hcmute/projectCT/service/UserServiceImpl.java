package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.User.RegisterRequest;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.exception.RegistrationException;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.model.UserStatus;
import com.hcmute.projectCT.repository.UserRepository;
import com.hcmute.projectCT.repository.UserStatusRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public void register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        existingUser.ifPresent(user -> {
            throw new RegistrationException(HttpStatus.BAD_REQUEST.value(), MessageKey.EMAIL_ALREADY_EXISTS);
        });
        try {
            UserStatus newUserStatus = UserStatus.builder()
                    .isNew(true)
                    .build();
            User newUser = User.builder()
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .status(newUserStatus)
                    .build();

            newUserStatus.setUser(newUser);
            userStatusRepository.save(newUserStatus);
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Error occurred during user registration", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }
}
