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

        try {
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String userId = null;
        String email = "eee";
        UserEntity userEntity = null;

        if (oauthClientName.equals("kakao")) {
            userId = "kakao_" + oAuth2User.getAttributes().get("id");
            userEntity = new UserEntity(userId, email, "kakao");
        }
        if (oauthClientName.equals("naver")) {
            Map<String, String> resMap = (Map<String, String>) oAuth2User.getAttributes().get("response");
            userId = "naver_" + resMap.get("id").substring(0, 14);
            email = resMap.get("email");
            userEntity = new UserEntity(userId, email, "naver");
        }

        userRepository.save(userEntity);

        return new CustomOAuth2UserEntity(userId);

    }
}
