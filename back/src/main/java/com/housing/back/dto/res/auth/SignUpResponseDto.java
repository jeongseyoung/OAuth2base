package com.housing.back.dto.res.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;
import com.housing.back.dto.res.ResponseDto;

import lombok.Getter;

@Getter
public class SignUpResponseDto extends ResponseDto {

    private SignUpResponseDto() {
        super();
    }

    public static ResponseEntity<SignUpResponseDto> success() {
        return ResponseEntity.ok().body(new SignUpResponseDto());
    }

    public static ResponseEntity<ResponseDto> duplicatedId() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(ResponseCode.DUPLICATED_ID, ResponseMessage.DUPLICATED_ID));
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL));
    }
}
