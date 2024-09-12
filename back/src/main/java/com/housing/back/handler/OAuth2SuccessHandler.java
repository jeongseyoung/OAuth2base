package com.housing.back.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.housing.back.entity.CustomOAuth2UserEntity;
import com.housing.back.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    // oauth 로그인 성공시 실행
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        CustomOAuth2UserEntity customOAuth2UserEntity = (CustomOAuth2UserEntity) authentication.getPrincipal();

        String userId = customOAuth2UserEntity.getName();
        String token = jwtProvider.create(userId);

        response.sendRedirect("http://localhost:4040/auth/oauth-response/" + token + "/3600");
    }
}
