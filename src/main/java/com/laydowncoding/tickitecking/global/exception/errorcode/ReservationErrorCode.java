package com.laydowncoding.tickitecking.global.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ReservationErrorCode {

    NOT_FOUND_SEAT(HttpStatus.BAD_REQUEST, "[ERROR] 존재하지 않는 좌석입니다."),
    NOT_AVAILABLE_SEAT(HttpStatus.BAD_REQUEST, "[ERROR] 예약할 수 없는 좌석입니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST, "[ERROR] 존재하지 않는 예약입니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "[ERROR] 본인만 취소할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ReservationErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
