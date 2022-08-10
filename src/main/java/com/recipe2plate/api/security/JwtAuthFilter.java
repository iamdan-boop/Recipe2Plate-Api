package com.recipe2plate.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe2plate.api.dto.response.ErrorDto;
import com.recipe2plate.api.entities.AppUser;
import com.recipe2plate.api.exceptions.NoRecordFoundException;
import com.recipe2plate.api.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final AppUserRepository appUserRepository;

    private final JwtTokenUtil jwtTokenUtil;

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
            try {
                SecurityContextHolder.getContext().setAuthentication(validateToken(splitHeader[1]));
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                MAPPER.writeValue(response.getOutputStream(), new ErrorDto("Unauthenticated"));
            }
        }
        filterChain.doFilter(request, response);
    }


    private Authentication validateToken(String token) {
        final AppUser appUser = appUserRepository.findByEmail(
                jwtTokenUtil.validateTokenWithSubject(token)
        ).orElseThrow(() -> new NoRecordFoundException("Unauthenticated"));
        return new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                List.of((GrantedAuthority) () -> (appUser.getRole().getRoleName()))
        );
    }
}
