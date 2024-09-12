package com.housing.back.service;

import org.springframework.http.ResponseEntity;

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

public interface AuthService {
    ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);

    ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);

    ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto);

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

}
