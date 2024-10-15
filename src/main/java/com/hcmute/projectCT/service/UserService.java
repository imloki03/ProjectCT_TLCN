package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.RegisterRequest;
import com.hcmute.projectCT.exception.RegistrationException;
import jakarta.transaction.Transactional;

public interface UserService {
    public void register(RegisterRequest request) ;
}
