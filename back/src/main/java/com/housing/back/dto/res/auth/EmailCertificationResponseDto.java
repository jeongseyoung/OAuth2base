package com.housing.back.dto.res.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;
import com.housing.back.dto.res.ResponseDto;

import lombok.Getter;

@Getter
public class EmailCertificationResponseDto extends ResponseDto {
    private EmailCertificationResponseDto() {
        super();
    }

    public static ResponseEntity<EmailCertificationResponseDto> success() {
        return ResponseEntity.ok().body(new EmailCertificationResponseDto());
    }

    public static ResponseEntity<ResponseDto> duplicatedId() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(ResponseCode.DUPLICATED_ID, ResponseMessage.DUPLICATED_ID));
    }

    public static ResponseEntity<ResponseDto> mailSendFailed() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL));
    }
}
