package org.eclipse.digitaltwin.basyx.aasrepository.http;

import java.io.IOException;
import java.util.Base64;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class BasicAuthFilter extends OncePerRequestFilter {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return;
        }

        String base64Credentials = authHeader.substring(6); // Remove "Basic "
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] split = credentials.split(":", 2);
        String username = split[0];
        String password = split[1];

        if (!USERNAME.equals(username) || !PASSWORD.equals(password)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
