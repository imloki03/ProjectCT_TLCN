package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.AuthResponse;
import com.hcmute.projectCT.dto.Token;
import com.hcmute.projectCT.dto.User.UserResponse;

public interface AuthService {
    AuthResponse login(String username, String password);
    Token generateToken();
}
