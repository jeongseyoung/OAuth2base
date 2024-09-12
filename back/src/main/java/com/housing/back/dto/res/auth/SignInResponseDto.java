package com.housing.back.dto.res.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;
import com.housing.back.dto.res.ResponseDto;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {

    private String token;
    private int expirationTime;

    public SignInResponseDto(String token) {
        super();
        this.token = token;
        this.expirationTime = 3600; // 한시간
    }

    public static ResponseEntity<SignInResponseDto> success(String token) {
        return ResponseEntity.ok().body(new SignInResponseDto(token));
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL));
    }
}
