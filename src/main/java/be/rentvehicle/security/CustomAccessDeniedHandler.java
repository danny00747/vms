package be.rentvehicle.security;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    public static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional.of(auth).ifPresent((authentication) -> log.warn("User: " + authentication.getName() + " attempted to access the protected admin URL: " + request.getRequestURI()));

        Map<String, String> message = Map.of(
                "error", "Access Denied",
                "message", "You are not authorized to perform the requested operation");
        response.setContentType("application/json");
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, message);
        out.close();
    }

}
