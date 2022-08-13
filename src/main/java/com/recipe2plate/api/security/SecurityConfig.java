package com.recipe2plate.api.security;

import com.recipe2plate.api.repositories.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {


    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserRepository appUserRepository;


    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint, JwtTokenUtil jwtTokenUtil, AppUserRepository appUserRepository) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.jwtTokenUtil = jwtTokenUtil;
        this.appUserRepository = appUserRepository;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/storage/**",
                    "/webjars/**");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint).and()
                .addFilterBefore(new JwtAuthFilter(appUserRepository, jwtTokenUtil), BasicAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/signIn", "/signUp").permitAll()
                .anyRequest().permitAll();
        return httpSecurity.build();
    }
}
