package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.EditProfileRequest;
import com.hcmute.projectCT.dto.User.RegisterRequest;

public interface UserService {
    public void register(RegisterRequest request) ;

    public void editProfile(EditProfileRequest editProfileRequest, String username);
}
