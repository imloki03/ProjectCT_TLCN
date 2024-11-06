package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.UserResponse;

public interface AuthService {
    UserResponse login(String username, String password);
}
