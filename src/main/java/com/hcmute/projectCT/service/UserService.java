package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.*;

public interface UserService {
    public void register(RegisterRequest request) ;

    public void editProfile(EditProfileRequest editProfileRequest, String username);

    public void editProfileAvatar(EditUserAvatarRequest request, String username);

    public UserResponse getUserInfo(String username);

    public void changePassword(ChangePasswordRequest request, String username);
}
