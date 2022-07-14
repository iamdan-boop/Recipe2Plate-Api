package com.recipe2plate.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe2plate.api.dto.response.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    public final UserAuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String[] splitHeader = authorizationHeader.split(" ");
        if (splitHeader.length == 2 && "Bearer".equals(splitHeader[0])) {
            System.out.println(splitHeader[1]);
            try {
                SecurityContextHolder.getContext().setAuthentication(
                        authenticationProvider.validateToken(splitHeader[1])
                );
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                MAPPER.writeValue(response.getOutputStream(), new ErrorDto("Unauthenticated"));
            }
        }
        filterChain.doFilter(request, response);
    }
}
