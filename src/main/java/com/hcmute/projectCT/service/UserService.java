package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.User.*;

import java.util.List;

public interface UserService {
    public void register(RegisterRequest request) ;

    public void editProfile(EditProfileRequest editProfileRequest, String username);

    public void editProfileAvatar(EditUserAvatarRequest request, String username);

    public UserResponse getUserInfo(String username);

    public UserResponse searchUserByExactlyUsernameOrEmail(String keyword);
  
    public void changePassword(ChangePasswordRequest request, String username);

    public void activateUser(String username);

    public List<AllAssignedTaskResponse> getAllAssignedTaskAtAllProject(String username);

    public void introduceUserAccount(String username);
}
