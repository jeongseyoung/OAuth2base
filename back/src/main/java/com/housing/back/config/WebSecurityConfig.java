package com.housing.back.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.housing.back.filter.JwtAuthenticationFilter;
import com.housing.back.handler.OAuth2SuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService defaultOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        System.out.println("httpSecurity: " + httpSecurity.toString());
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource())).csrf(CsrfConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/", "/api/v1/auth/**", "/oauth2/**").permitAll()
                        .requestMatchers("/api/v1/user/**").hasRole("USER") // ROLE_는 빼고
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .oauth2Login(oauth -> oauth.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/oauth2"))
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(defaultOAuth2UserService))
                        .successHandler(oAuth2SuccessHandler))

                .exceptionHandling(e -> e.authenticationEntryPoint(new FailedAuthenticationEntryPoint())) // 인증실패시 실행
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration cosConfiguration = new CorsConfiguration();

        cosConfiguration.addAllowedOrigin("*");
        cosConfiguration.addAllowedMethod("*");
        cosConfiguration.addAllowedHeader("*");
        // configuration.addExposedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", cosConfiguration);

        return source;
    }
}

// 인증 실패시 실행
class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        System.out.println("FailedAuthenticationEntryPoint");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{ \"code\": \"NP\", \"message\": \"Authorization failed\"}");

    }
}
