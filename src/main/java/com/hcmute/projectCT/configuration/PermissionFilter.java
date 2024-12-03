package com.hcmute.projectCT.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.enums.Permission;
import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.repository.CollaboratorRepository;
import com.hcmute.projectCT.service.CollaboratorService;
import com.hcmute.projectCT.util.PermissionUtil;
import com.hcmute.projectCT.util.WebUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class PermissionFilter extends OncePerRequestFilter {
    private final PermissionUtil permissionUtil;
    private final CollaboratorRepository collaboratorRepository;
    private final WebUtil webUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        Permission requiredPermission = permissionUtil.getRequiredPermission(path, method);
        if (requiredPermission != null) {
            String projectId = request.getHeader("Project-Id");
            if (projectId == null) {
                writeErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Missing Project ID");
                return;
            }

            Collaborator collaborator = collaboratorRepository.findByProject_IdAndUser_Username(Long.parseLong(projectId), webUtil.getCurrentUsername());
            if (collaborator == null || collaborator.getPermission().compareTo(requiredPermission) < 0) {
                writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN, "You don't have permission to perform this action");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, int status, String desc) throws IOException {
        RespondData<Object> errorResponse = new RespondData<>();
        errorResponse.setStatus(status);
        errorResponse.setDesc(desc);
        errorResponse.setToken(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setContentType("application/json");
        response.setStatus(status);
        response.getWriter().write(jsonResponse);
    }

}
