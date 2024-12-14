package com.hcmute.projectCT.service;

import com.hcmute.projectCT.dto.AuthResponse;
import com.hcmute.projectCT.dto.User.UserResponse;
import com.hcmute.projectCT.exception.LoginFailedException;
import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.UserRepository;
import com.hcmute.projectCT.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/projectct_db?useSSL=false&serverTimezone=UTC",
        "spring.datasource.username=root",
        "spring.datasource.password=Xc.Nvt.2003",
        "spring.jpa.hibernate.ddl-auto=update",
        "spring.jackson.time-zone=GMT",
        "spring.mail.host=smtp.gmail.com",
        "spring.mail.port=587",
        "spring.mail.username=testmail@gmail.com",
        "spring.mail.password=testmailpassword",
        "server.address=127.0.0.1",
        "server.port=8080",
        "jwt.secret_key=mySecretKey",
        "jwt.expired_day=7",
        "jwt.expired_hour=24",
        "gemini.api.key=myGeminiAPIKey",
        "time_zone=GMT"
})
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("hashedPassword");
        mockUser.setName("Test User");
        mockUser.setEmail("test@example.com");
        mockUser.setAvatarURL("avatarURL");
        mockUser.setGender("Male");
    }

    @Test
    void testLogin_Success() {
        try (MockedStatic<PasswordUtil> passwordUtilMock = mockStatic(PasswordUtil.class)) {
            // Mock behavior
            when(userRepository.findByUsernameOrEmail(anyString(), anyString())).thenReturn(mockUser);
            passwordUtilMock.when(() -> PasswordUtil.checkPassword(any(), any())).thenReturn(true);
            when(jwtService.generateToken(any(), anyLong())).thenReturn("mockAccessToken");
            when(jwtService.generateToken(any(), anyLong())).thenReturn("mockRefreshToken");
            System.out.println("User from repository: " + userRepository.findByUsernameOrEmail("testuser", "test@example.com").getUsername());
            System.out.println("Password check result: " + PasswordUtil.checkPassword("password", mockUser.getPassword()));
            // Call the method
            AuthResponse<UserResponse> response = new AuthResponse<UserResponse>();
            try {
                response = authService.login("testuser", "hashedPassword");
            } catch (Exception e) {
                response.setData(new UserResponse());
            }

            // Assertions
            assertNotNull(response);
            assertEquals("testuser", response.getData().getUsername());
            assertNotNull(response.getToken().getAccess_token());
            assertNotNull(response.getToken().getRefresh_token());
        }
    }

    @Test
    void testLogin_Failure_InvalidPassword() {
        try (MockedStatic<PasswordUtil> passwordUtilMock = mockStatic(PasswordUtil.class)) {
            // Mock behavior for invalid password
            when(userRepository.findByUsernameOrEmail(any(), any())).thenReturn(mockUser);
            passwordUtilMock.when(() -> PasswordUtil.checkPassword(any(), any())).thenReturn(false);

            // Call the method and assert exception
            LoginFailedException exception = assertThrows(LoginFailedException.class, () -> {
                authService.login("testuser", "wrongpassword");
            });

            assertEquals(HttpStatus.UNAUTHORIZED.value(), exception.getErrorCode());
        }
    }
}
