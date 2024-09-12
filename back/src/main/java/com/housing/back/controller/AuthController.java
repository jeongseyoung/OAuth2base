package com.housing.back.controller;

import org.springframework.web.bind.annotation.RestController;

import com.housing.back.dto.req.auth.CheckCertificationRequestDto;
import com.housing.back.dto.req.auth.EmailCertificationRequestDto;
import com.housing.back.dto.req.auth.IdCheckRequestDto;
import com.housing.back.dto.req.auth.SignInRequestDto;
import com.housing.back.dto.req.auth.SignUpRequestDto;
import com.housing.back.dto.res.auth.CheckCertificationResponseDto;
import com.housing.back.dto.res.auth.EmailCertificationResponseDto;
import com.housing.back.dto.res.auth.IdCheckResponseDto;
import com.housing.back.dto.res.auth.SignInResponseDto;
import com.housing.back.dto.res.auth.SignUpResponseDto;
import com.housing.back.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController // requestBody 반환가능
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/check-id")
    public ResponseEntity<? super IdCheckResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto requestBody) {
        ResponseEntity<? super IdCheckResponseDto> res = authService.idCheck(requestBody);
        System.out.println(res.getStatusCode() + " " + res.getBody());
        return res;
    }

    @PostMapping("/email-certi")
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(
            @RequestBody @Valid EmailCertificationRequestDto requestBody) {
        ResponseEntity<? super EmailCertificationResponseDto> res = authService.emailCertification(requestBody);
        System.out.println("인증메일 전송완료");
        return res;
    }

    @PostMapping("/check-certification")
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(
            @RequestBody @Valid CheckCertificationRequestDto requestBody) {
        ResponseEntity<? super CheckCertificationResponseDto> res = authService.checkCertification(requestBody);
        return res;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto dto) {
        return authService.signUp(dto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signUp(@RequestBody @Valid SignInRequestDto dto) {
        return authService.signIn(dto);
    }
}
