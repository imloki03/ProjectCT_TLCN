package com.hcmute.projectCT.util;

import com.hcmute.projectCT.enums.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Component
public class PermissionUtil {
    private final Map<Endpoint, Permission> apiPermissionMap = new LinkedHashMap<>();

    public PermissionUtil() {
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/project/.*"), HttpMethod.DELETE), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/project/.*"), HttpMethod.PUT), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/project/.*"), HttpMethod.PATCH), Permission.OWNER);

        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/backlog/.*"), HttpMethod.POST), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/backlog/.*"), HttpMethod.PUT), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/backlog/.*"), HttpMethod.PATCH), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/backlog/.*"), HttpMethod.DELETE), Permission.OWNER);

        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/phase/.*"), HttpMethod.POST), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/phase/.*"), HttpMethod.PUT), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/phase/.*"), HttpMethod.DELETE), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/phase/\\d+/\\d+/\\d+/status/.*"), HttpMethod.PATCH), Permission.COLLAB);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/phase/\\d+/\\d+/backlog/.*"), HttpMethod.PATCH), Permission.OWNER);

        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/collab/.*"), HttpMethod.POST), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/collab/.*"), HttpMethod.PATCH), Permission.OWNER);
        apiPermissionMap.put(new Endpoint(Pattern.compile("/api/v1/collab/.*"), HttpMethod.DELETE), Permission.OWNER);
    }

    public Permission getRequiredPermission(String apiPath, HttpMethod method) {
        for (Map.Entry<Endpoint, Permission> entry : apiPermissionMap.entrySet()) {
            Endpoint endpoint = entry.getKey();
            if (endpoint.matches(apiPath, method)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static class Endpoint {
        private final Pattern pathPattern;
        private final HttpMethod method;

        public Endpoint(Pattern pathPattern, HttpMethod method) {
            this.pathPattern = pathPattern;
            this.method = method;
        }

        public boolean matches(String apiPath, HttpMethod method) {
            return this.pathPattern.matcher(apiPath).matches() && this.method == method;
        }
    }
}
