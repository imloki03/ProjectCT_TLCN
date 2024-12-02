package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.AuthResponse;
import com.hcmute.projectCT.dto.Token;
import com.hcmute.projectCT.dto.User.UserResponse;
import com.hcmute.projectCT.exception.LoginFailedException;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.UserRepository;
import com.hcmute.projectCT.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    final UserRepository userRepository;
    final JwtService jwtService;
    final TokenUtil tokenUtil;
    @Value("${jwt.expired_day}")
    private long expiredDay;
    @Value("${jwt.expired_hour}")
    private long expiredHour;
    @Override
    public AuthResponse login(String username, String password) {
        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new LoginFailedException(HttpStatus.UNAUTHORIZED.value(), MessageKey.LOGIN_FAILED);
        }
        var accessToken = jwtService.generateToken(user, expiredHour*60*60*1000);
        var refreshToken = jwtService.generateToken(user, expiredDay*24*60*60*1000);
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
                .token(Token
                        .builder()
                        .access_token(accessToken)
                        .refresh_token(refreshToken)
                        .build())
                .build();
    }

    @Override
    public Token generateToken() {
        return tokenUtil.generateToken();
    }


}
