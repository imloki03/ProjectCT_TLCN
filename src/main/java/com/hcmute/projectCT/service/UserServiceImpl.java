package com.hcmute.projectCT.service;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.User.*;
import com.hcmute.projectCT.exception.InternalServerException;
import com.hcmute.projectCT.exception.RegistrationException;
import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.model.UserStatus;
import com.hcmute.projectCT.repository.*;
import com.hcmute.projectCT.util.PasswordUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final TagRepository tagRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        User existingUserName = userRepository.findByUsername(request.getUsername());

        existingUser.ifPresent(user -> {
            throw new RegistrationException(HttpStatus.BAD_REQUEST.value(), MessageKey.EMAIL_ALREADY_EXISTS);
        });
        if (existingUserName != null) {
            throw new RegistrationException(HttpStatus.BAD_REQUEST.value(), MessageKey.USERNAME_ALREADY_EXISTS);
        }

        try {
            UserStatus newUserStatus = UserStatus.builder()
                    .isNew(true)
                    .build();
            User newUser = toUserEntity(request);
            newUser.setStatus(newUserStatus);
            newUserStatus.setUser(newUser);
            userStatusRepository.save(newUserStatus);
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Error occurred during user registration", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void editProfile(EditProfileRequest request, String username) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new InternalServerException(HttpStatus.BAD_REQUEST.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            User updatedUser = toUserEntity(request, existingUser);
            userRepository.save(updatedUser);
        }
        catch (Exception e) {
            log.error("Error occurred during edit user profile", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void editProfileAvatar(EditUserAvatarRequest request, String username) {
        User existingUser = userRepository.findByUsername(username);
        if (existingUser == null) {
            throw new InternalServerException(HttpStatus.NOT_FOUND.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            existingUser.setAvatarURL(request.getAvatarURL());
            userRepository.save(existingUser);
        }
        catch (Exception e) {
            log.error("Error occurred during edit user profile", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public UserResponse getUserInfo(String username) {
        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null) {
            throw new RegistrationException(HttpStatus.NOT_FOUND.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            return new UserResponse(user);
        } catch (Exception e) {
            log.error("Error occurred while fetching user info for username: {}", username, e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public UserResponse searchUserByExactlyUsernameOrEmail(String keyword) {
        if (keyword.trim().isEmpty()) {
            return null;
        }
        User user = userRepository.findByUsernameOrEmail(keyword, keyword);
        if (user==null) {
            return null;
        }
        return new UserResponse(user);
    }

    public void changePassword(ChangePasswordRequest request, String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RegistrationException(HttpStatus.NOT_FOUND.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            user.setPassword(PasswordUtil.hashPassword(request.getPassword()));
            userRepository.save(user);
        }
        catch (Exception e) {
            log.error("Error occurred during change user password", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public void activateUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RegistrationException(HttpStatus.NOT_FOUND.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            user.getStatus().setActivated(true);
            userRepository.save(user);
        }
        catch (Exception e) {
            log.error("Error occurred during change user password", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    @Override
    public List<AllAssignedTaskResponse> getAllAssignedTaskAtAllProject(String username) {
        User user = userRepository.findByUsername(username);
        List<Project> projects = projectRepository.findByOwner_UsernameOrCollaboratorList_User_Username(username, username);
        List<AllAssignedTaskResponse> tasks = new ArrayList<>();
        for (Project project : projects) {
            Collaborator collab = collaboratorRepository.findByProjectAndUser(project, user).orElse(null);
            if (!collab.getTaskList().isEmpty())
                tasks.add(new AllAssignedTaskResponse(Objects.requireNonNull(collab).getTaskList(), project));
        }
        return tasks;
    }

    @Override
    public void introduceUserAccount(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RegistrationException(HttpStatus.NOT_FOUND.value(), MessageKey.USER_NOT_FOUND);
        }

        try {
            user.getStatus().setNew(false);
            userRepository.save(user);
        }
        catch (Exception e) {
            log.error("Error occurred during change user password", e);
            throw new InternalServerException(HttpStatus.INTERNAL_SERVER_ERROR.value(), MessageKey.SERVER_ERROR);
        }
    }

    private User toUserEntity(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .password(PasswordUtil.hashPassword(request.getPassword()))
                .email(request.getEmail())
                .gender(request.getGender())
                .build();
    }

    private User toUserEntity(EditProfileRequest request, User existingUser) {
        return existingUser.toBuilder()
                .name(request.getName())
                .gender(request.getGender())
                .tagList(tagRepository.findAllById(request.getTagList()))
                .build();
    }
}
