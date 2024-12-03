package com.hcmute.projectCT.util;

import com.hcmute.projectCT.model.User;
import com.hcmute.projectCT.repository.UserRepository;
import com.hcmute.projectCT.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class WebUtil {
    final JwtService jwtService;
    final UserRepository userRepository;
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public User getCurrentUser() {
        HttpServletRequest request = getCurrentRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String username = jwtService.getUserName(token);
            return userRepository.findByUsername(username);
        }
        return null;
    }

    public String getCurrentUsername() {
        HttpServletRequest request = getCurrentRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            return jwtService.getUserName(token);
        }
        return null;
    }
}
