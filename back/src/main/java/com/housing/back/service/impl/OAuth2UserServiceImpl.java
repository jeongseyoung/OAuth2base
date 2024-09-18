package com.housing.back.service.impl;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.housing.back.entity.CustomOAuth2UserEntity;
import com.housing.back.entity.UserEntity;
import com.housing.back.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest req) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(req);

        // clinet name 가져오기
        String oauthClientName = req.getClientRegistration().getClientName();
        System.out.println("oauthClientName: " + oauthClientName);

        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userId = null;
        // String email = "eee";
        String email = null;
        UserEntity userEntity = null;

        if (oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            System.out.println("userId: " + userId);
            userEntity = new UserEntity(userId, "eee", "kakao");
        }
        if (oauthClientName.equals("naver")) {
            @SuppressWarnings("unchecked")
            Map<String, String> resMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + resMap.get("id").substring(0, 14);
            email = resMap.get("email");
            userEntity = new UserEntity(userId, email, "naver");
        }
        if (oauthClientName.equals("Google")) {
            userId = oAuth2User.getAttribute("given_name");
            email = oAuth2User.getAttribute("email");
            System.out.println("GOOGLE : " + userId + " " + email);
            userEntity = new UserEntity(userId, email, "Google");
        }

        userRepository.save(userEntity);

        return new CustomOAuth2UserEntity(userId);

    }
}
