package com.laydowncoding.tickitecking.global.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ConcertErrorCode {

    NOT_FOUND_AUDITORIUM(HttpStatus.BAD_REQUEST, "[ERROR] 존재하지 않는 공연장입니다."),
    NOT_FOUND_CONCERT(HttpStatus.BAD_REQUEST, "[ERROR] 존재하지 않는 콘서트입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ConcertErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
