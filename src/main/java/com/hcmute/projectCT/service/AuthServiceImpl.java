package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.AuthResponse;
import com.hcmute.projectCT.dto.User.UserResponse;
import com.hcmute.projectCT.exception.LoginFailedException;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    final UserRepository userRepository;
    final JwtService jwtService;
    @Override
    public AuthResponse login(String username, String password) {
        //xu li authentication sau khi co register
//        User user = userRepository.findByUsername(username);
        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new LoginFailedException(HttpStatus.UNAUTHORIZED.value(), MessageKey.LOGIN_FAILED);
        }
        var jwtToken = jwtService.generateToken(user);
        var userRes = UserResponse
                .builder()
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .avatarURL(user.getAvatarURL())
                .gender(user.getGender())
                .build();
        return AuthResponse
                .builder()
                .data(userRes)
                .token(jwtToken)
                .build();
    }
}
