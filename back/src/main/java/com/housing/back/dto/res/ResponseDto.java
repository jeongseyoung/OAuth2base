package com.housing.back.dto.res;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.common.ResponseCode;
import com.housing.back.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    private String code;
    private String message;

    public ResponseDto() {
        this.code = ResponseCode.SUCCESS;
        this.message = ResponseMessage.SUCCESS;
    }

    public static ResponseEntity<ResponseDto> DB_Error() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR));
    }

    public static ResponseEntity<ResponseDto> ValidationFailed() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL));
    }
}
