package com.laydowncoding.tickitecking.global.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {

    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "[ERROR] 이미 존재하는 이름입니다."),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "[ERROR] 이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.BAD_REQUEST, "[ERROR] 이미 존재하는 닉네임입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

}
