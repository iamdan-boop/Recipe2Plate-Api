package com.recipe2plate.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe2plate.api.dto.ErrorDto;
import com.recipe2plate.api.dto.request.LoginRequest;
import com.recipe2plate.api.exceptions.BadCredentialsException;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if ("/api/signIn".equals(request.getServletPath()) &&
                HttpMethod.POST.matches(request.getMethod())) {
            final LoginRequest loginRequest = MAPPER.readValue(request.getInputStream(), LoginRequest.class);
            try {
                final Authentication authentication = userAuthenticationProvider.validateCredentials(loginRequest);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException | NoRecordFoundException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                MAPPER.writeValue(response.getOutputStream(), new ErrorDto("Invalid Credentials"));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
