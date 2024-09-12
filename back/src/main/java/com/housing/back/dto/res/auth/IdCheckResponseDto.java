package com.housing.back.dto.res.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;
import com.housing.back.dto.res.ResponseDto;

import lombok.Getter;

@Getter
public class IdCheckResponseDto extends ResponseDto {

    public IdCheckResponseDto() {
        super();
    }

    public static ResponseEntity<IdCheckResponseDto> success() {
        return ResponseEntity.ok().body(new IdCheckResponseDto());
    }

    public static ResponseEntity<ResponseDto> duplicatedId() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(ResponseCode.DUPLICATED_ID, ResponseMessage.DUPLICATED_ID));
    }
}
