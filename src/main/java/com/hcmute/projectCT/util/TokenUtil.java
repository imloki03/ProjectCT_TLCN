package com.hcmute.projectCT.util;

import com.hcmute.projectCT.dto.Token;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenUtil {
    final JwtService jwtService;
    final WebUtil webUtil;
    @Value("${jwt.expired_day}")
    private long expiredDay;
    @Value("${jwt.expired_hour}")
    private long expiredHour;
    public Token generateToken() {
        User user = webUtil.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        var accessToken = jwtService.generateToken(user, expiredHour*60*60*1000);
        var refreshToken = jwtService.generateToken(user, expiredDay*24*60*60*1000);
        return Token
                .builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();
    }
}
