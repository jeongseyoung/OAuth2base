package com.housing.back.dto.res.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;
import com.housing.back.dto.res.ResponseDto;

import lombok.Getter;

@Getter
public class CheckCertificationResponseDto extends ResponseDto {
    private CheckCertificationResponseDto() {
        super();
    }

    public static ResponseEntity<CheckCertificationResponseDto> success() {
        return ResponseEntity.ok().body(new CheckCertificationResponseDto());
    }

    public static ResponseEntity<ResponseDto> certificationFail() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL));
    }
}
