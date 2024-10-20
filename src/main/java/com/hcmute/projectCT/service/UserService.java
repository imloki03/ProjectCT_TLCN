package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.EditProfileRequest;
import com.hcmute.projectCT.dto.User.EditUserAvatarRequest;
import com.hcmute.projectCT.dto.User.RegisterRequest;
import com.hcmute.projectCT.dto.User.UserResponse;

public interface UserService {
    public void register(RegisterRequest request) ;

    public void editProfile(EditProfileRequest editProfileRequest, String username);

    public void editProfileAvatar(EditUserAvatarRequest request, String username);

    public UserResponse getUserInfo(String username);
}
